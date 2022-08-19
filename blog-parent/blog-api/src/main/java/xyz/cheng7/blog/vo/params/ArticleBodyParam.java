package xyz.cheng7.blog.vo.params;

import lombok.Data;

@Data
public class ArticleBodyParam {
    private Long id;
    private String content;
    private String contentHtml;
}
