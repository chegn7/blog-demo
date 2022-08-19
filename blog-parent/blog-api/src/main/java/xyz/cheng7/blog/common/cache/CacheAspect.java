package xyz.cheng7.blog.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.util.RedisUtil;
import xyz.cheng7.blog.vo.Result;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class CacheAspect {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(xyz.cheng7.blog.common.cache.Cache)")
    public void cachePointcut() {}

    @Around("cachePointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Signature signature = proceedingJoinPoint.getSignature();
            String classSimpleName = proceedingJoinPoint.getClass().getSimpleName();
            String methodName = signature.getName();

            Object[] args = proceedingJoinPoint.getArgs();
            Class[] paramTypes = new Class[args.length];
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (null != args[i]) {
                    paramTypes[i] = args[i].getClass();
                    params.append(JSONUtil.getInstance().toJSON(args[i]));
                }
            }
            if (StringUtils.isNotBlank(params) && params.length() > 16) {
                params = new StringBuilder(DigestUtils.md5Hex(params.toString()));
            }
            Method method = signature.getDeclaringType().getMethod(methodName, paramTypes);
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            String cacheName = cacheAnnotation.name();
            String redisKey = RedisUtil.getCacheKey(cacheName, classSimpleName, methodName, params.toString());
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotBlank(redisValue)) {
                log.info(String.format("经由缓存, %s , %s", classSimpleName, methodName));
                return JSONUtil.getInstance().toObject(redisValue, Result.class);
            }
            long expireTimeMills = cacheAnnotation.expireTimeMills();
            Object res = proceedingJoinPoint.proceed();
            redisValue = JSONUtil.getInstance().toJSON(res);
            redisTemplate.opsForValue().set(redisKey, redisValue, expireTimeMills, TimeUnit.MILLISECONDS);
            log.info(String.format("存入缓存, %s, %s", classSimpleName, methodName));
            return res;
        } catch (Throwable e) {
            throw new RuntimeException("缓存aop异常");
        }
    }
}
