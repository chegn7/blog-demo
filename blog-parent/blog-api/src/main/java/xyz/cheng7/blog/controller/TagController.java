package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cheng7.blog.service.TagService;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.TagVo;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // tags/hot
    @GetMapping("hot")
    public Result hot() {
        int limit = 5;
        List<TagVo> tagVos = tagService.getHotTags(limit);
        return Result.success(tagVos);

    }

    @GetMapping
    public Result getAllTags() {
        List<TagVo> tagVos = tagService.findAllTags();
        return Result.success(tagVos);
    }

    @GetMapping("detail")
    public Result getTagsDetail() {
        List<TagVo> tagVos = tagService.findAllTags();
        return Result.success(tagVos);
    }

    @GetMapping("detail/{id}")
    public Result getTagDetail(@PathVariable("id") Long tagId) {
        TagVo tagVo = tagService.findTagByTagId(tagId);
        if (null == tagVo) return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        return Result.success(tagVo);
    }
}
