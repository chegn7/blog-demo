package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.cheng7.blog.dao.pojo.ArticleScore;
import xyz.cheng7.blog.service.ArticleScoreService;
import xyz.cheng7.blog.dao.mapper.ArticleScoreMapper;
import org.springframework.stereotype.Service;
import xyz.cheng7.blog.service.ArticleViewCountsService;
import xyz.cheng7.blog.service.CommentService;
import xyz.cheng7.blog.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* @author c
* @description 针对表【ms_article_score】的数据库操作Service实现
* @createDate 2022-08-19 17:49:31
*/
@Service
public class ArticleScoreServiceImpl extends ServiceImpl<ArticleScoreMapper, ArticleScore>
    implements ArticleScoreService{
    @Autowired
    ArticleScoreMapper articleScoreMapper;

    @Autowired
    ArticleViewCountsService articleViewCountsService;

    @Autowired
    CommentService commentService;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public int updateByArticleId(List<Long> ids) {
        int cnt = 0;
        for (Long id : ids) {
            cnt += this.updateByArticleId(id);
        }
        return cnt;
    }

    @Override
    public int updateByArticleId(Long articleId) {
        Integer articleViewCounts = articleViewCountsService.getArticleViewCounts(articleId);
        Integer commentCounts = commentService.getCommentCounts(articleId);
        Double score = articleViewCounts + commentCounts * 2.0;
        ArticleScore articleScore = articleScoreMapper.selectByArticleId(articleId);
        if (null == articleScore) {
            articleScore = new ArticleScore(articleId, score);
            return articleScoreMapper.insertArticleScore(articleScore);
        } else {
            articleScore.setScore(score);
            return articleScoreMapper.updateArticleScore(articleScore);
        }
    }

    @Override
    public List<Long> getHotIds(int limit) {
        List<Long> ids = new ArrayList<>();
        String redisKey = RedisUtil.getHotArticleIds();
        Set<String> set = redisTemplate.opsForZSet().reverseRange(redisKey, 0, limit - 1);
        if (null == set || set.size() == 0) {
            List<ArticleScore> hot = articleScoreMapper.selectHot(limit);
            for (ArticleScore articleScore : hot) {
                ids.add(articleScore.getArtilceId());
                redisTemplate.opsForZSet().add(redisKey, articleScore.getArtilceId().toString(), articleScore.getScore());
            }
        } else {
            for (String o : set) {
                ids.add(Long.parseLong(o));
            }
        }
        return ids;
    }
}




