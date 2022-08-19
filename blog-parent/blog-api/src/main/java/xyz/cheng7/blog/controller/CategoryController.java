package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.dao.pojo.Category;
import xyz.cheng7.blog.service.CategoryService;
import xyz.cheng7.blog.vo.CategoryVo;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;

import java.util.List;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Result getAllCategories() {
        List<CategoryVo> categoryVos = categoryService.findAllCategories();
        return Result.success(categoryVos);
    }

    @GetMapping("detail")
    public Result getCategoriesDetail() {
        List<CategoryVo> categoryVos = categoryService.findAllCategories();
        return Result.success(categoryVos);
    }

    @GetMapping("detail/{id}")
    public Result getCategoryDetail(@PathVariable Long id) {
        Category category = categoryService.findCategoryById(id);
        if (null == category) return Result.failure(ErrorCode.OTHER_ERROR.getCode(), ErrorCode.OTHER_ERROR.getMsg());
        return Result.success(category);
    }

}
