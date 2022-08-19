package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.cheng7.blog.dao.pojo.AdminPermission;
import xyz.cheng7.blog.service.AdminPermissionService;
import xyz.cheng7.blog.dao.mapper.AdminPermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author c
* @description 针对表【ms_admin_permission】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission>
    implements AdminPermissionService{

}




