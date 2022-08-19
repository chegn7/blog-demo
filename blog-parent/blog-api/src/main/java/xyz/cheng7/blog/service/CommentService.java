package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.vo.CommentVo;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.CommentParam;

import java.util.List;

/**
* @author c
* @description 针对表【ms_comment】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface CommentService extends IService<Comment> {

    List<CommentVo> findComments(Long articleId);

    Result createComment(CommentParam commentParam);

    Integer getCommentCounts(Long articleId);
}
