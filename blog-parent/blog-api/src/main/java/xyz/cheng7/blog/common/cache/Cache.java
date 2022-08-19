package xyz.cheng7.blog.common.cache;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    long expireTimeMills() default 1 * 60 * 1000;
    String name() default "";
}
