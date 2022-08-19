package xyz.cheng7.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentParam {
    private Long articleId;
    private String content;
    private Long parent;
    private Long toUserId;
}
