package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.service.LoginService;
import xyz.cheng7.blog.service.TokenService;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.RegisterParams;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public Result register(@RequestBody RegisterParams registerParams) {
        Result result = loginService.register(registerParams);
        return result;
    }
}
