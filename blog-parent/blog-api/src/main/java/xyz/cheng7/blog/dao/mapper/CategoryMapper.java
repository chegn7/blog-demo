package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author c
* @description 针对表【ms_category】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Category selectByCategoryId(Long categoryId);

    List<Category> selectCategories();
}




