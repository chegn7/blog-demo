package xyz.cheng7.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.service.ArticleService;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.util.RedisUtil;
import xyz.cheng7.blog.vo.EventTopic;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${redis.article.new.set.max-size}")
    private Long maxNewIdSize;

    @KafkaListener(topics = {EventTopic.CREATE_COMMENT})
    public void handleCommentCreateEvent(ConsumerRecord record) {
        Event event = preprocessRecord(record);
        if (null == event) return;
        String scoreKey = RedisUtil.getToBeUpdatedArticleIds();
        String articleIdStr = (String) event.getData().get("articleId");
        redisTemplate.opsForSet().add(scoreKey, articleIdStr);
        String redisKey = RedisUtil.getArticleComments(Long.valueOf(articleIdStr));
        redisTemplate.delete(redisKey);
    }

    @KafkaListener(topics = {EventTopic.PUBLISH_ARTICLE})
    public void handlePublishArticle(ConsumerRecord record) {
        Event event = preprocessRecord(record);
        if (null == event) return;
        log.info("收到事件，{}", JSONUtil.getInstance().toJSON(event));
        String redisKey = RedisUtil.getNewArticleIds();
        // 更新最新文章的ids
        Double createDateDouble = (Double) event.getData().get("createDate");
        Long createDate = createDateDouble.longValue();
        redisTemplate.opsForZSet().add(redisKey, event.getEntityId().toString(), createDate);
        // 弹出最旧的文章，也就是createDate最小的，score最小的文章
        Long size = redisTemplate.opsForZSet().size(redisKey);
        if (size > maxNewIdSize) {
            // 分布式锁
            String lockKey = RedisUtil.getDeleteNewIdLock();
            RLock rLock = redissonClient.getLock(lockKey);
            try {
                // 最多等待500ms，上锁后2000ms自动解锁
                boolean lockRes = rLock.tryLock(500, 2000, TimeUnit.MILLISECONDS);
                if (lockRes) {
                    size = redisTemplate.opsForZSet().size(redisKey);
                    if (size > maxNewIdSize) {
                        Long deletedSize = size - maxNewIdSize;
                        Long removeRange = redisTemplate.opsForZSet().removeRange(redisKey, 0, deletedSize - 1);
                        if (deletedSize == removeRange) {
                            log.info("本次删除{}个id成功", deletedSize);
                        }
                    }
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            } finally {
                rLock.unlock();
            }
        }
        String scoreKey = RedisUtil.getToBeUpdatedArticleIds();
        redisTemplate.opsForSet().add(scoreKey, event.getEntityId().toString());
    }

    private Event preprocessRecord(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            log.error("空事件");
            return null;
        }
        Event event = JSONUtil.getInstance().toObject(record.value().toString(), Event.class);
        if (event == null) {
            log.error("事件格式错误");
            return null;
        }
        return event;
    }
}
