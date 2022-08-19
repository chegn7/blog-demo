package xyz.cheng7.blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.admin.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author c
* @description 针对表【ms_admin】的数据库操作Mapper
* @createDate 2022-08-14 12:00:25
* @Entity xyz.cheng7.blog.admin.pojo.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    Admin selectByUsername(String username);
}




