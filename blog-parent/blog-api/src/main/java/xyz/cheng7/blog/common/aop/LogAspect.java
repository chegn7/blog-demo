package xyz.cheng7.blog.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.util.HttpContextUtil;
import xyz.cheng7.blog.util.IpUtil;
import xyz.cheng7.blog.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(xyz.cheng7.blog.common.aop.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTimestamp = System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long consumedTime = System.currentTimeMillis() - startTimestamp;
        record(joinPoint, consumedTime);
        return res;
    }

    private void record(ProceedingJoinPoint joinPoint, long time) {
        log.info("-----------------------start log----------------------");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 拿到方法的注解
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info(String.format("module:%s", logAnnotation.module()));
        log.info(String.format("operation:%s", logAnnotation.operation()));

        // 请求的类
        String className = joinPoint.getTarget().getClass().getName();
        log.info(String.format("request class:%s", className));

        log.info(String.format("request method:%s", methodSignature.getName()));

        // 请求参数
        String params = JSONUtil.getInstance().toJSON(joinPoint.getArgs()[0]);
        log.info(String.format("params:%s", params));

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ipAddr = IpUtil.getIpAddr(request);
        log.info(String.format("from IP:%s", ipAddr));

        log.info(String.format("execution time:%s ms", time));
        log.info("-----------------------end log----------------------");
    }
}
