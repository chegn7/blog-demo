package xyz.cheng7.blog.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cheng7.blog.dao.pojo.ArticleBody;

@Data
@NoArgsConstructor
public class ArticleBodyVo {

    private String content;

    public ArticleBodyVo(ArticleBody articleBody) {
        this.content = articleBody.getContent();
    }
}
