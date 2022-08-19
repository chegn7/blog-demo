package xyz.cheng7.blog.dao.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName ms_article_view_counts
 */
@Data
public class ArticleViewCounts implements Serializable {
    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章浏览量
     */
    private Integer viewCounts;

    private static final long serialVersionUID = 1L;
}