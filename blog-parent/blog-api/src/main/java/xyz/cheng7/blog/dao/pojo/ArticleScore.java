package xyz.cheng7.blog.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName ms_article_score
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleScore implements Serializable {
    /**
     * 
     */
    private Long artilceId;

    /**
     * 
     */
    private Double score;

    private static final long serialVersionUID = 1L;
}