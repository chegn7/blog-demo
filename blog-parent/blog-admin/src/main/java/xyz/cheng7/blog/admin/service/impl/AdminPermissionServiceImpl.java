package xyz.cheng7.blog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cheng7.blog.admin.pojo.AdminPermission;
import xyz.cheng7.blog.admin.pojo.Permission;
import xyz.cheng7.blog.admin.service.AdminPermissionService;
import xyz.cheng7.blog.admin.mapper.AdminPermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author c
* @description 针对表【ms_admin_permission】的数据库操作Service实现
* @createDate 2022-08-14 13:58:05
*/
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission>
    implements AdminPermissionService{
    @Autowired
    AdminPermissionMapper adminPermissionMapper;

    @Override
    public List<Permission> findPermissionsByUserId(Long userId) {
        return adminPermissionMapper.selectByUserId(userId);
    }
}




