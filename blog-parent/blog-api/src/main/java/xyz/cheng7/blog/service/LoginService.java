package xyz.cheng7.blog.service;

import org.springframework.transaction.annotation.Transactional;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.LoginParams;
import xyz.cheng7.blog.vo.params.RegisterParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    Result logout(String jwt);

    @Transactional
    Result register(RegisterParams registerParams);
}
