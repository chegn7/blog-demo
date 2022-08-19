package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.service.LoginService;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.LoginParams;

@RestController
@RequestMapping("login")
public class LoginController {
    /**
     * 为什么不直接引入userService？
     * service专门负责各自的功能，userService负责对用户表增删改查
     * 登录需要整合其他的功能，用一个专门的service负责
     */
    @Autowired
    private LoginService loginService;

    /**
     * 接口url：/login
     * 请求方式：POST
     * 请求参数：
     * | 参数名称 | 参数类型 | 说明 |
     * | -------- | -------- | ---- |
     * | account  | string   | 账号 |
     * | password | string   | 密码 |
     * |          |          |      |
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        Result result = loginService.login(loginParams);
        return result;
    }
}
