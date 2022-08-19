package xyz.cheng7.blog.admin.service;

import xyz.cheng7.blog.admin.comdel.params.PageParam;
import xyz.cheng7.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.admin.vo.Result;

import java.util.List;

/**
* @author c
* @description 针对表【ms_permission】的数据库操作Service
* @createDate 2022-08-14 10:57:46
*/
public interface PermissionService extends IService<Permission> {

    Result listPermission(PageParam pageParam);

    Result addPermission(Permission permission);

    Result updatePermission(Permission permission);

    Result deletePermission(Long id);

    List<Permission> findPermissionsByUserId(Long userId);
}
