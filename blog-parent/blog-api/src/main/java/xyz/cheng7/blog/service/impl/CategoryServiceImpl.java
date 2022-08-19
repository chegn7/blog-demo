package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import xyz.cheng7.blog.dao.pojo.Category;
import xyz.cheng7.blog.service.CategoryService;
import xyz.cheng7.blog.dao.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.vo.CategoryVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author c
* @description 针对表【ms_category】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<CategoryVo> findAllCategories() {
        List<Category> categories = categoryMapper.selectCategories();
        if (CollectionUtils.isEmpty(categories)) return Collections.emptyList();
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo(category);
            categoryVos.add(categoryVo);
        }
        return categoryVos;
    }

}




