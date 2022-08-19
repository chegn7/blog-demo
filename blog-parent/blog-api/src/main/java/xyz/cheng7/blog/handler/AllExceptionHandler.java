package xyz.cheng7.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.cheng7.blog.vo.Result;

@ControllerAdvice
@Slf4j
public class AllExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e) {
        log.info(e.getMessage());
        return Result.failure(-1, "系统异常");
    }
}
