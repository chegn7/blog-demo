package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.dao.mapper.CommentMapper;
import xyz.cheng7.blog.dao.pojo.Comment;
import xyz.cheng7.blog.event.EventProducer;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.service.SysUserService;
import xyz.cheng7.blog.util.IDUtil;
import xyz.cheng7.blog.util.TimeUtil;
import xyz.cheng7.blog.util.UserThreadLocal;
import xyz.cheng7.blog.vo.*;
import xyz.cheng7.blog.vo.params.CommentParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author c
 * @description 针对表【ms_comment】的数据库操作Service实现
 * @createDate 2022-08-08 15:02:19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IDUtil idUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EventProducer producer;

    @Override
    public List<CommentVo> findComments(Long articleId) {
        List<Comment> parents = null;
        List<CommentVo> commentVos = null;
        // parentId 为0 即为直接对文章的评论
        parents = commentMapper.selectCommentByIds(articleId, 0L);
        if (null == parents) {
            return Collections.emptyList();
        }
        commentVos = new ArrayList<>();
        for (Comment parent : parents) {
            commentVos.add(findChildren(convertToCommentVo(parent), articleId));
        }

        return commentVos;
    }

    @Override
    public Result createComment(CommentParam commentParam) {
        Comment comment = convertToComment(commentParam);
        if (null == comment)
            return Result.failure(ErrorCode.NOT_LOGIN_ERROR.getCode(), ErrorCode.NOT_LOGIN_ERROR.getMsg());
        int i = commentMapper.insertComment(comment);
        if (i == 1) {
            Event event = new Event()
                    .setTopic(EventTopic.CREATE_COMMENT)
                    .setCreatorId(comment.getAuthorId())
                    .setEntityId(comment.getId())
                    .setData("articleId", comment.getArticleId().toString());
            producer.fireEvent(event);
            return Result.success(null);
        } else return Result.failure(ErrorCode.COMMENT_CREATE_ERROR.getCode(), ErrorCode.COMMENT_CREATE_ERROR.getMsg());
    }

    @Override
    public Integer getCommentCounts(Long articleId) {
        return commentMapper.selectCommentCountsByArticleId(articleId);
    }

    private Comment convertToComment(CommentParam commentParam) {
        SysUserVo userVo = UserThreadLocal.get();
        if (null == userVo) return null;
        Long parent = commentParam.getParent();
        parent = null == parent ? 0L : parent;
        Long toUserId = commentParam.getToUserId();
        toUserId = null == toUserId ? 0L : toUserId;
        Comment comment = new Comment();
        comment.setId(idUtil.generateID());
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setParentId(parent);
        comment.setToUid(toUserId);
        comment.setCreateDate(System.currentTimeMillis());
        comment.setAuthorId(Long.parseLong(userVo.getId()));
        comment.setLevel(parent == 0 ? "1" : "2");
        return comment;
    }

    private CommentVo convertToCommentVo(Comment comment) {
        if (null == comment) return null;
        CommentVo commentVo = new CommentVo();
        commentVo.setId(String.valueOf(comment.getId()));
        commentVo.setLevel(comment.getLevel());
        commentVo.setContent(comment.getContent());
        commentVo.setAuthor(findCommentSender(comment.getAuthorId()));
        commentVo.setToUser(findCommentSender(comment.getToUid()));
        commentVo.setCreateDate(TimeUtil.longToString(comment.getCreateDate()));
        commentVo.setChildrens(null);
        return commentVo;
    }

    private SysUserVo findCommentSender(Long authorId) {
        SysUserVo userVo = new SysUserVo();
        SysUserVo rawUserVo = sysUserService.findSysUser(authorId);
        if (null == rawUserVo) return null;
        userVo.setId(String.valueOf(authorId));
        userVo.setNickname(rawUserVo.getNickname());
        userVo.setAvatar(rawUserVo.getAvatar());
        return userVo;
    }

    private CommentVo findChildren(CommentVo parent, Long articleId) {
        List<Comment> childrenComment = commentMapper.selectCommentByIds(articleId, Long.parseLong(parent.getId()));
        if (CollectionUtils.isEmpty(childrenComment)) {
            parent.setChildrens(Collections.emptyList());
            return parent;
        }
        // 如果childrenComment不为空
        List<CommentVo> children = new ArrayList<>();
        for (Comment comment : childrenComment) {
            CommentVo commentVo = convertToCommentVo(comment);
            commentVo = findChildren(commentVo, articleId);
            children.add(commentVo);
        }
        parent.setChildrens(children);
        return parent;
    }


}




