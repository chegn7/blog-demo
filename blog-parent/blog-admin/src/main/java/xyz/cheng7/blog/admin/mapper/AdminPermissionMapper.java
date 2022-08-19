package xyz.cheng7.blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.admin.pojo.AdminPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.cheng7.blog.admin.pojo.Permission;

import java.util.List;

/**
* @author c
* @description 针对表【ms_admin_permission】的数据库操作Mapper
* @createDate 2022-08-14 13:58:05
* @Entity xyz.cheng7.blog.admin.pojo.AdminPermission
*/
@Mapper
public interface AdminPermissionMapper extends BaseMapper<AdminPermission> {

    List<Permission> selectByUserId(Long userId);
}




