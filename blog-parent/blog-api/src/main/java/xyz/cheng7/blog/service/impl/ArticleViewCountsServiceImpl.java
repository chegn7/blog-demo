package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.dao.mapper.ArticleViewCountsMapper;
import xyz.cheng7.blog.dao.pojo.ArticleViewCounts;
import xyz.cheng7.blog.service.ArticleViewCountsService;
import xyz.cheng7.blog.util.RedisUtil;

import java.util.List;

/**
 * @author c
 * @description 针对表【ms_article_view_counts】的数据库操作Service实现
 * @createDate 2022-08-17 13:58:55
 */
@Service
public class ArticleViewCountsServiceImpl extends ServiceImpl<ArticleViewCountsMapper, ArticleViewCounts>
        implements ArticleViewCountsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleViewCountsMapper articleViewCountsMapper;

    @Value("${redis.article.default-expire-time}")
    private Long articleRedisExpireTime;

    @Override
    public Integer getArticleViewCounts(Long articleId) {
        Integer viewCounts = null;
        String redisKey = RedisUtil.getArticleViewCount(articleId);
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (null == redisValue) {
            ArticleViewCounts articleViewCounts = articleViewCountsMapper.selectByArticleId(articleId);
            if (null != articleViewCounts) {
                viewCounts = articleViewCounts.getViewCounts();
                redisValue = viewCounts;
                redisTemplate.opsForValue().set(redisKey, redisValue.toString());
            }
        } else {
            viewCounts = Integer.parseInt((String) redisValue);
        }
        return viewCounts;
    }


    @Override
    public List<Long> getHotArticleId(int limit) {
        List<Long> ids = null;
        ids = articleViewCountsMapper.selectHotId(limit);
        return ids;
    }

    @Override
    public Integer insertNewArticle(Long articleId) {
        ArticleViewCounts viewCounts = new ArticleViewCounts();
        viewCounts.setViewCounts(0);
        viewCounts.setArticleId(articleId);
        return articleViewCountsMapper.insert(viewCounts);
    }

    @Override
    public int updateViewCount(Long articleId, Integer viewCount) {
        ArticleViewCounts articleViewCounts = new ArticleViewCounts();
        articleViewCounts.setArticleId(articleId);
        articleViewCounts.setViewCounts(viewCount);
        return articleViewCountsMapper.updateViewCountsInt(articleViewCounts);
    }
}




