package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.ArticleViewCounts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_view_counts】的数据库操作Mapper
* @createDate 2022-08-17 13:58:55
* @Entity xyz.cheng7.blog.dao.pojo.ArticleViewCounts
*/
@Mapper
public interface ArticleViewCountsMapper extends BaseMapper<ArticleViewCounts> {

    List<Long> selectHotId(int limit);

    ArticleViewCounts selectByArticleId(Long articleId);

    int insertArticleViewCount(ArticleViewCounts articleViewCounts);

    int updateViewCountsInt(ArticleViewCounts articleViewCounts);
}




