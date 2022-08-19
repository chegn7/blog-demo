package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.ArticleBody;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author c
* @description 针对表【ms_article_body】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.ArticleBody
*/
@Mapper
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {

    ArticleBody selectArticleBodyById(Long bodyId);
}




