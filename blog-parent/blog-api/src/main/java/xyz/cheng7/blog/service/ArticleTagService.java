package xyz.cheng7.blog.service;

import xyz.cheng7.blog.dao.pojo.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_tag】的数据库操作Service
* @createDate 2022-08-08 15:02:19
*/
public interface ArticleTagService extends IService<ArticleTag> {


    List<ArticleTag> findArticleTagByTagId(Long tagId);
}
