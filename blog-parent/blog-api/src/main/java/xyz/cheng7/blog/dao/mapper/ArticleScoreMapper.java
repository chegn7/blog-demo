package xyz.cheng7.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.ArticleScore;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_score】的数据库操作Mapper
* @createDate 2022-08-19 17:49:31
* @Entity xyz.cheng7.blog.dao.pojo.ArticleScore
*/
@Mapper
public interface ArticleScoreMapper extends BaseMapper<ArticleScore> {

    List<ArticleScore> selectHot(int limit);

    ArticleScore selectByArticleId(Long articleId);

    int insertArticleScore(ArticleScore articleScore);

    int updateArticleScore(ArticleScore articleScore);
}




