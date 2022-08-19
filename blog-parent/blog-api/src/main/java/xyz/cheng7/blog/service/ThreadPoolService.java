package xyz.cheng7.blog.service;

import org.springframework.data.redis.core.RedisTemplate;

public interface ThreadPoolService {
    void updateArticleViewCount(Long articleId, RedisTemplate redisTemplate);
}
