package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.ArticleViewCounts;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_view_counts】的数据库操作Service
* @createDate 2022-08-17 13:58:55
*/
public interface ArticleViewCountsService extends IService<ArticleViewCounts> {

    Integer getArticleViewCounts(Long articleId);

    List<Long> getHotArticleId(int limit);

    Integer insertNewArticle(Long articleId);

    int updateViewCount(Long articleId, Integer viewCount);
}
