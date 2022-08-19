package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.ArticleBody;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author c
* @description 针对表【ms_article_body】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface ArticleBodyService extends IService<ArticleBody> {

    ArticleBody findArticleBodyByBodyId(Long bodyId);

    int insertArticleBody(ArticleBody articleBody);
}
