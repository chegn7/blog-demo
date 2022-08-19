package xyz.cheng7.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import xyz.cheng7.blog.dao.dos.Archives;
import xyz.cheng7.blog.dao.pojo.Article;
import xyz.cheng7.blog.vo.ArticleVo;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.ArticleParam;
import xyz.cheng7.blog.vo.params.PageParams;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface ArticleService extends IService<Article> {

    List<ArticleVo> listArticle(PageParams pageParams);

    List<ArticleVo> hotArticle(int limit);

    List<ArticleVo> newArticle(int limit);

    List<Archives> listArchives();

    Result findArticleById(Long articleId);

    @Transactional
    ArticleVo publish(ArticleParam articleParam);
}
