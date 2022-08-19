package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author c
* @description 针对表【ms_sys_user】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserById(Long userId);

    SysUser getUserByAccount(String account);
}




