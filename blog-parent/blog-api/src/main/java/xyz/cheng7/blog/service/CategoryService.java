package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.vo.CategoryVo;

import java.util.List;

/**
* @author c
* @description 针对表【ms_category】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface CategoryService extends IService<Category> {

    Category findCategoryById(Long categoryId);

    List<CategoryVo> findAllCategories();
}
