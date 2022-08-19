package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.service.SysUserService;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.SysUserVo;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    SysUserService userService;


    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String jwt) {
        SysUserVo userVo = userService.findSysUser(jwt);
        if (null == userVo) return Result.failure(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMsg());
        userVo.setEmail(null);
        userVo.setMobilePhoneNumber(null);
        return Result.success(userVo);
    }

}
