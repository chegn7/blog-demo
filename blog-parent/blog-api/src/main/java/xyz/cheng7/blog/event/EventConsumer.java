package xyz.cheng7.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.service.ArticleService;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.util.RedisUtil;
import xyz.cheng7.blog.vo.EventTopic;

@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    @KafkaListener(topics = {EventTopic.CREATE_COMMENT})
    public void handleCommentCreateEvent(ConsumerRecord record) {
        Event event = preprocessRecord(record);
        if (null == event) return;
    }

    @KafkaListener(topics = {EventTopic.PUBLISH_ARTICLE})
    public void handlePublishArticle(ConsumerRecord record) {
        Event event = preprocessRecord(record);
        if (null == event) return;
        String redisKey = RedisUtil.getNewArticleIds();
        Long size = redisTemplate.opsForZSet().size(redisKey);
        if (null == size || size == 0) return;
        // 更新最新文章的ids
        Long createDate = (Long) event.getData().get("createDate");
        redisTemplate.opsForZSet().add(redisKey, event.getEntityId(), createDate);
        // 弹出最旧的文章，也就是createDate最小的，score最小的文章
        ZSetOperations.TypedTuple typedTuple = redisTemplate.opsForZSet().popMin(redisKey);
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
