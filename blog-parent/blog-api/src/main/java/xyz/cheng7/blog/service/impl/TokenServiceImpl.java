package xyz.cheng7.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.service.TokenService;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.util.JWTUtil;
import xyz.cheng7.blog.util.RedisUtil;
import xyz.cheng7.blog.vo.SysUserVo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Override
    public SysUserVo check(String jwt) {
        // 1. 判空
        if (StringUtils.isBlank(jwt)) return null;
        // 2. 能否解析
        Map<String, Object> tokenBody = JWTUtil.checkToken(jwt);
        if (null == tokenBody) return null;
        // 3. redis判断
        String userVoJSON = redisTemplate.opsForValue().get(RedisUtil.getJWTKey(jwt));
        if (StringUtils.isBlank(userVoJSON)) return null;
        return JSONUtil.getInstance().toObject(userVoJSON, SysUserVo.class);
    }

    @Override
    public String generateToken(SysUserVo userVo) {
        String token = JWTUtil.createToken(Long.parseLong(userVo.getId()));
        String userVoJSON = JSONUtil.getInstance().toJSON(userVo);
        redisTemplate.opsForValue().set(RedisUtil.getJWTKey(token), userVoJSON, 1, TimeUnit.DAYS);
        return token;
    }

    @Override
    public void deleteToken(String jwt) {
        redisTemplate.delete(RedisUtil.getJWTKey(jwt));
    }
}
