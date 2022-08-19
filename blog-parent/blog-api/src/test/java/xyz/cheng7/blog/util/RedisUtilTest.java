package xyz.cheng7.blog.util;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class RedisUtilTest extends TestCase {
    @Test
    public void testGetCacheKey() {
        System.out.println(RedisUtil.getCacheKey("", "a", "", new StringBuilder().toString()));
    }
}