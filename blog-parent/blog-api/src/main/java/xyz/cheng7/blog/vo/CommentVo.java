package xyz.cheng7.blog.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private String id;
    private SysUserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private String level;
    private SysUserVo toUser;
}
