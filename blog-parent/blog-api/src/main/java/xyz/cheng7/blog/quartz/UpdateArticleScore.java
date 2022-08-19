package xyz.cheng7.blog.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.cheng7.blog.service.ArticleScoreService;
import xyz.cheng7.blog.util.RedisUtil;

@Slf4j
public class UpdateArticleScore implements Job {
    @Autowired
    ArticleScoreService articleScoreService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisUtil.getToBeUpdatedArticleIds();
        BoundSetOperations setOps = redisTemplate.boundSetOps(redisKey);
        while (setOps.size() > 0) {
            String articleIdStr = (String) setOps.pop();
            articleScoreService.updateByArticleId(Long.valueOf(articleIdStr));
        }
        // 更新完分数后删除缓存
        redisTemplate.delete(RedisUtil.getHotArticleIds());
    }
}
