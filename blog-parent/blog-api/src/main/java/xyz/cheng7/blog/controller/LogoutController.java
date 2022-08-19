package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.service.LoginService;
import xyz.cheng7.blog.vo.Result;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String jwt) {
        return loginService.logout(jwt);
    }
}
