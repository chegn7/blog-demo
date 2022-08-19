package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.dao.mapper.SysUserMapper;
import xyz.cheng7.blog.dao.pojo.SysUser;
import xyz.cheng7.blog.service.SysUserService;
import xyz.cheng7.blog.service.TokenService;
import xyz.cheng7.blog.util.SaltUtil;
import xyz.cheng7.blog.vo.SysUserVo;

/**
 * @author c
 * @description 针对表【ms_sys_user】的数据库操作Service实现
 * @createDate 2022-08-08 15:02:19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenService tokenService;


    @Override
    public SysUserVo findSysUser(Long userId) {
        if (userId == 0) return null;
        SysUser user = sysUserMapper.getUserById(userId);
        if (user == null) {
            return new SysUserVo();
        }
        SysUserVo userVo = pojoToVo(user);
        return userVo;
    }

    @Override
    public SysUserVo findSysUser(String account, String password) {
        // 1. 查用户
        SysUser user = sysUserMapper.getUserByAccount(account);
        if (null == user) return null;
        // 2. 匹配密码
        password = SaltUtil.getPasswordWithSalt(password, user.getSalt());
        if (user.getPassword().equals(password)) return pojoToVo(user);
        else return null;
    }

    @Override
    public SysUserVo findSysUser(String jwt) {
        SysUserVo userVo = tokenService.check(jwt);
        if (null == userVo) return null;
        return userVo;
    }

    @Override
    public SysUserVo findSysUserByAccount(String account) {
        SysUser user = sysUserMapper.getUserByAccount(account);
        if (null == user) return null;
        return pojoToVo(user);
    }

    private SysUserVo pojoToVo(SysUser user) {
        // user 已经不为null
        SysUserVo userVo = new SysUserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setId(String.valueOf(user.getId()));
        return userVo;
    }


}




