package xyz.cheng7.blog.admin.service;

import xyz.cheng7.blog.admin.pojo.AdminPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.admin.pojo.Permission;

import java.util.List;

/**
* @author c
* @description 针对表【ms_admin_permission】的数据库操作Service
* @createDate 2022-08-14 13:58:05
*/
public interface AdminPermissionService extends IService<AdminPermission> {

    List<Permission> findPermissionsByUserId(Long userId);
}
