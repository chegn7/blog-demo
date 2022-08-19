package xyz.cheng7.blog.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author c
* @description 针对表【ms_permission】的数据库操作Mapper
* @createDate 2022-08-14 10:57:46
* @Entity xyz.cheng7.blog.admin.pojo.Permission
*/
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectByUserId(Long userId);
}




