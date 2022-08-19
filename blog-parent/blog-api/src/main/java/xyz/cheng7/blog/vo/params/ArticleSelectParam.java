package xyz.cheng7.blog.vo.params;

import lombok.Data;

@Data
public class ArticleSelectParam {
    private Long articleId;
    private Long startTime;
    private Long endTime;
    private Long categoryId;
    private Long tagId;
    private Integer limit;
    private Integer offset;
}
