package xyz.cheng7.blog.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.cheng7.blog.service.ArticleViewCountsService;
import xyz.cheng7.blog.util.MyCollectionUtil;
import xyz.cheng7.blog.util.RedisUtil;

import java.util.List;
import java.util.Set;

@Slf4j
public class UpdateViewCount2DB implements Job {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleViewCountsService articleViewCountsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String prefix = RedisUtil.getArticleViewCount(null);
        Set<String> keys = redisTemplate.keys(prefix.concat("*"));
        if (null == keys || keys.size() == 0) return;
        List<String> deletedKeys = MyCollectionUtil.deletedViewCountsKeyList;
        for (String key : keys) {
            Long articleId = Long.valueOf(StringUtils.substringAfterLast(key, RedisUtil.getSplit()));
            try {
                Integer viewCount = Integer.parseInt((String) redisTemplate.opsForValue().get(key));
                log.info(String.format("即将更新文章id: %s, 浏览量: %s", articleId, viewCount));
                int cnt = articleViewCountsService.updateViewCount(articleId, viewCount);
                if (cnt == 1) {
                    redisTemplate.delete(key);
//                    deletedKeys.add(key);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                log.error("更新浏览量到数据库失败:" + key);
            }
        }
        // 给消息队列发消息，双删
        if (deletedKeys.size() > 0) {

        }
    }
}
