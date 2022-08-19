package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cheng7.blog.dao.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author c
* @description 针对表【ms_comment】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectCommentByArticleId(Long articleId);

    List<Comment> selectParentCommentByArticleId(Long articleId);

    List<Comment> selectCommentByIds(@Param("articleId") Long articleId, @Param("parentId") Long parentId);

    int insertComment(Comment comment);

    Integer selectCommentCountsByArticleId(Long articleId);
}




