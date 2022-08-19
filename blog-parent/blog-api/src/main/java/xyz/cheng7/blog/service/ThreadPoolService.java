package xyz.cheng7.blog.service;

import org.springframework.data.redis.core.RedisTemplate;
import xyz.cheng7.blog.dao.pojo.Article;

public interface ThreadPoolService {
    void updateArticleViewCount(Article article, RedisTemplate redisTemplate);
}
