package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.vo.SysUserVo;

/**
* @author c
* @description 针对表【ms_sys_user】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface SysUserService extends IService<SysUser> {
    SysUserVo findSysUser(Long userId);

    SysUserVo findSysUser(String account, String password);

    SysUserVo findSysUser(String jwt);


    SysUserVo findSysUserByAccount(String account);
}
