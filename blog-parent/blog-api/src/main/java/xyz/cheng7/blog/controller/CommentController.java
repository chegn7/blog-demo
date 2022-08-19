package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.vo.CommentVo;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.CommentParam;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("article/{id}")
    public Result viewArticleComments(@PathVariable(value = "id") Long articleId) {
        if (null == articleId) return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        List<CommentVo> commentVos =  commentService.findComments(articleId);
        if (null == commentVos) return Result.failure(ErrorCode.COMMENT_GET_ERROR.getCode(), ErrorCode.COMMENT_GET_ERROR.getMsg());
        return Result.success(commentVos);
    }

    @PostMapping("create/change")
    public Result createComment(@RequestBody CommentParam commentParam) {
        return commentService.createComment(commentParam);
    }

}
