package xyz.cheng7.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.dao.pojo.ArticleScore;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_score】的数据库操作Service
* @createDate 2022-08-19 17:49:31
*/
public interface ArticleScoreService extends IService<ArticleScore> {
    int updateByArticleId(List<Long> ids);

    int updateByArticleId(Long articleId);

    List<Long> getHotIds(int limit);
}
