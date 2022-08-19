package xyz.cheng7.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.dao.pojo.Article;
import xyz.cheng7.blog.service.ThreadPoolService;
import xyz.cheng7.blog.util.RedisUtil;
@Slf4j
@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {

//    @Override
//    @Async("taskExecutor")
//    public void updateArticleViewCount(Article article, ArticleMapper articleMapper) {
//        Integer originCounts = article.getViewCounts();
//        Integer updatedCounts = originCounts + 1;
//        Long articleId = article.getId();
//        articleMapper.updateViewCounts(articleId, originCounts, updatedCounts);
//    }

    @Override
    @Async("taskExecutor")
    public void updateArticleViewCount(Article article, RedisTemplate redisTemplate) {
        String redisKey = RedisUtil.getArticleViewCount(article.getId());
        Long increment = redisTemplate.opsForValue().increment(redisKey);
        log.info(String.format("更新了阅读数， %s : %s", article.getId(), increment));
    }
}
