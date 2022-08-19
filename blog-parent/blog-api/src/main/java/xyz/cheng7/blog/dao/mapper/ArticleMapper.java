package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.cheng7.blog.dao.dos.Archives;
import xyz.cheng7.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.cheng7.blog.vo.params.ArticleSelectParam;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Long> selectHotArticle(int limit);

    List<Article> selectNewArticle(int limit);

    List<Archives> listArchives();

    Integer updateViewCounts(@Param("articleId") Long articleId,@Param("originCounts") Integer originCounts,@Param("updatedCounts") Integer updatedCounts);

    int countArticle(ArticleSelectParam selectParam);

    List<Article> selectArticle(ArticleSelectParam selectParam);

    List<Long> selectNewIds(int limit);

    List<Article> selectNew(int limit);
}




