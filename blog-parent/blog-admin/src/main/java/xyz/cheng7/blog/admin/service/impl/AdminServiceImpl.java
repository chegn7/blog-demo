package xyz.cheng7.blog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.admin.mapper.AdminMapper;
import xyz.cheng7.blog.admin.pojo.Admin;
import xyz.cheng7.blog.admin.pojo.Permission;
import xyz.cheng7.blog.admin.service.AdminService;
import xyz.cheng7.blog.admin.service.PermissionService;

import java.util.List;

/**
 * @author c
 * @description 针对表【ms_admin】的数据库操作Service实现
 * @createDate 2022-08-14 12:00:25
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Admin findAdminByUsername(String username) {
        return adminMapper.selectByUsername(username);
    }

    @Override
    public List<Permission> findPermissionsByUserId(Long userId) {
        return permissionService.findPermissionsByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = findAdminByUsername(username);
        if (null == admin) return null;
        List<Permission> permissions = findPermissionsByUserId(admin.getId());
        admin.setPermissions(permissions);
        return admin;
    }
}




