package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.cheng7.blog.dao.pojo.Admin;
import xyz.cheng7.blog.service.AdminService;
import xyz.cheng7.blog.dao.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author c
* @description 针对表【ms_admin】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




