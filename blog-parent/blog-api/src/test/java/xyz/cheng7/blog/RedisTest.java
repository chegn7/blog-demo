package xyz.cheng7.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void setTest() {
        String redisKey = "set_test";
        Long articleId = 1L;
//        redisTemplate.opsForSet().add(redisKey, articleId.toString());
        System.out.println(redisTemplate.opsForSet().size(redisKey));
        BoundSetOperations ops = redisTemplate.boundSetOps(redisKey);
        while (ops.size() > 0) {
            System.out.println(ops.pop().getClass().getName());
        }
    }

    @Test
    public void zSetDelete() {
        String key = "zset-delete";
        redisTemplate.opsForZSet().add(key, "ttt", 1.0);
        System.out.println(redisTemplate.delete(key));
    }

    @Test
    public void zSetAdd() {
        String key = "zset-add";
        redisTemplate.opsForZSet().add(key, String.valueOf(1L), 1.0);
        redisTemplate.opsForZSet().add(key, String.valueOf(2L), 2.0);
        redisTemplate.opsForZSet().add(key, String.valueOf(3L), 3.0);
        redisTemplate.opsForZSet().add(key, String.valueOf(4L), 4.0);
        List<Long> ids = new ArrayList<>();
        Set<String> set = redisTemplate.opsForZSet().reverseRange(key, 0, 1);
        for (String o : set) {
            ids.add(Long.parseLong(o));
        }
        System.out.println(ids);
    }
}
