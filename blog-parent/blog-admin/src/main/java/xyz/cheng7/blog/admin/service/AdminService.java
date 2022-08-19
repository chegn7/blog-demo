package xyz.cheng7.blog.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.cheng7.blog.admin.pojo.Admin;
import xyz.cheng7.blog.admin.pojo.Permission;

import java.util.List;

/**
* @author c
* @description 针对表【ms_admin】的数据库操作Service
* @createDate 2022-08-14 12:00:25
*/
public interface AdminService extends IService<Admin>, UserDetailsService {

    Admin findAdminByUsername(String username);
    List<Permission> findPermissionsByUserId(Long userId);
}
