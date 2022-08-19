package xyz.cheng7.blog.service;

import xyz.cheng7.blog.vo.SysUserVo;

public interface TokenService {
    SysUserVo check(String jwt);

    String generateToken(SysUserVo userVo);

    void deleteToken(String jwt);
}
