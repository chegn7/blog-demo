package xyz.cheng7.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.vo.Result;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("login")
    public Result login() {
        return Result.success(null);
    }
}
