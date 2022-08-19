package xyz.cheng7.blog.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.vo.CommentVo;

import java.util.List;

@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    CommentService commentService;
    @Test
    public void findComments() {
        List<CommentVo> comments = commentService.findComments(1L);
        System.out.println(JSONUtil.getInstance().toJSON(comments));
    }
}