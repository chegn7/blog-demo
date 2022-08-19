package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.dao.dos.Archives;
import xyz.cheng7.blog.dao.mapper.ArticleMapper;
import xyz.cheng7.blog.dao.pojo.Article;
import xyz.cheng7.blog.dao.pojo.ArticleBody;
import xyz.cheng7.blog.dao.pojo.ArticleTag;
import xyz.cheng7.blog.dao.pojo.Category;
import xyz.cheng7.blog.event.EventProducer;
import xyz.cheng7.blog.service.*;
import xyz.cheng7.blog.util.*;
import xyz.cheng7.blog.vo.*;
import xyz.cheng7.blog.vo.params.ArticleBodyParam;
import xyz.cheng7.blog.vo.params.ArticleParam;
import xyz.cheng7.blog.vo.params.ArticleSelectParam;
import xyz.cheng7.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author c
 * @description 针对表【ms_article】的数据库操作Service实现
 * @createDate 2022-08-08 15:02:19
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private IDUtil idUtil;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${redis.article.default-expire-time}")
    private Long articleRedisExpireTime;


    @Autowired
    private ArticleViewCountsService articleViewCountsService;

    @Autowired
    private EventProducer producer;

    @Autowired
    private ArticleScoreService articleScoreService;

    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        ArticleSelectParam selectParam = new ArticleSelectParam();
        BeanUtils.copyProperties(pageParams, selectParam);
        if (pageParams.getYear() != null && pageParams.getMonth() != null) {
            DateTime startDate = TimeUtil.stringToDate(pageParams.getYear(), pageParams.getMonth());
            DateTime endDate = startDate.plusMonths(1);
            selectParam.setStartTime(startDate.getMillis());
            selectParam.setEndTime(endDate.getMillis() - 1);
        }
        int cnts = articleMapper.countArticle(selectParam);
        int[] limits = PageUtil.convertPageParamToSQLLimit(pageParams, cnts);
        if (null == limits) return Collections.emptyList();
        selectParam.setOffset(limits[0]);
        selectParam.setLimit(limits[1]);
        List<Article> articles = articleMapper.selectArticle(selectParam);
        return recordsToList(articles, true, true);
    }

    @Override
    public List<ArticleVo> hotArticle(int limit) {
        List<ArticleVo> articleVos = new ArrayList<>();
        List<Long> hotIds = articleScoreService.getHotIds(limit);
        for (Long hotId : hotIds) {
            articleVos.add(getArticleVo(hotId, false, false, false, false));
        }
        return articleVos;
    }


    @Override
    public List<ArticleVo> newArticle(int limit) {
        List<ArticleVo> articleVos = new ArrayList<>();
        List<Long> ids = this.getNewIds(limit);
        for (Long id : ids) {
            ArticleVo articleVo = getArticleVo(id, false, false, false, false);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    @Override
    public List<Archives> listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        if (CollectionUtils.isEmpty(archivesList)) {
            return Collections.emptyList();
        }
        return archivesList;
    }

    @Override
    public Result findArticleById(Long articleId) {
        ArticleVo articleVo = null;
        String redisKey = RedisUtil.getArticleVo(articleId, true, true, true, true);
        String redisValue = (String) redisTemplate.opsForValue().get(redisKey);
        if (null == redisValue) {
            synchronized (this) {
                redisValue = (String) redisTemplate.opsForValue().get(redisKey);
                if (null == redisValue) {
                    Article article = getArticleById(articleId);
                    if (null == article) {
                        return Result.failure(ErrorCode.ARTICLE_GET_ERROR.getCode(), ErrorCode.ARTICLE_GET_ERROR.getMsg());
                    }
                    articleVo = pojoToVo(article, true, true, true, true);
                    redisValue = JSONUtil.getInstance().toJSON(articleVo);
                    redisTemplate.opsForValue().set(redisKey, redisValue, articleRedisExpireTime, TimeUnit.SECONDS);
                }
            }
        }
        if (articleVo == null && redisValue != null) {
            articleVo = JSONUtil.getInstance().toObject(redisValue, ArticleVo.class);
        }
        /**
         * 查看完文章，新增阅读数
         * 1. 更新阅读数会加写锁，阻塞其他的读操作，降低性能
         * 2. 此接口需要返回读到的文章，更新出现问题，不应影响返回读的文章
         * 1 的性能损耗不方便降
         * 2 可以优化，使用线程池
         * 把更新操作放到线程池中执行，不影响主线程返回读到的文章内容
         */
        threadPoolService.updateArticleViewCount(Long.parseLong(articleVo.getId()), redisTemplate);
        Integer viewCounts = articleViewCountsService.getArticleViewCounts(articleId);
        articleVo.setViewCounts(viewCounts);
        redisTemplate.opsForSet().add(RedisUtil.getToBeUpdatedArticleIds(), articleId.toString());
        return Result.success(articleVo);
    }

    @Override
    public ArticleVo publish(ArticleParam articleParam) {
        Integer cnt = 0;
        // 先插入ArticleBody
        ArticleBody articleBody = convertToArticleBody(articleParam.getBody(), articleParam.getId());
        cnt = articleBodyService.insertArticleBody(articleBody);
        if (cnt != 1) return null;
        // 至此，bodyId 和 articleId都有了
        Article article = convertToArticle(articleParam, articleBody);
        cnt = this.insertArticle(article);
        if (cnt != 1) return null;
        cnt = articleViewCountsService.insertNewArticle(article.getId());
        if (cnt != 1) return null;
        List<TagVo> tagVos = articleParam.getTags();
        if (null != tagVos) {
            for (TagVo tagVo : tagVos) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.valueOf(tagVo.getId()));
                articleTag.setId(idUtil.generateID());
                cnt = tagService.insertArticleTag(articleTag);
                if (cnt != 1) return null;
            }
        }
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        Event event = new Event()
                .setTopic(EventTopic.PUBLISH_ARTICLE)
                .setCreatorId(article.getAuthorId())
                .setEntityId(article.getId())
                .setData("createDate", article.getCreateDate());
        producer.fireEvent(event);
        return articleVo;
    }

    @Override
    public List<Long> getNewIds(int limit) {
        List<Long> ids = new ArrayList<>();
        String redisKey = RedisUtil.getNewArticleIds();
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, 0, limit - 1);
        if (null == set || set.size() == 0) {
            synchronized (this) {
                set = redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1);
                if (null == set || set.size() == 0) {
                    List<Article> newArticles = articleMapper.selectNew(limit);
                    for (Article newArticle : newArticles) {
                        ids.add(newArticle.getId());
                        redisTemplate.opsForZSet().add(redisKey, newArticle.getId().toString(), newArticle.getCreateDate());
                    }
                    return ids;
                }
            }
        }
        if (null != set) {
            for (ZSetOperations.TypedTuple<String> tuple : set) {
                ids.add(Long.valueOf(tuple.getValue()));
            }
        }
        return ids;
    }

    private int insertArticle(Article article) {
        return articleMapper.insert(article);
    }

    private ArticleBody convertToArticleBody(ArticleBodyParam bodyParam, Long articleId) {
        Long bodyId = bodyParam.getId();
        if (null == bodyId) bodyId = idUtil.generateID();
        if (null == articleId) articleId = idUtil.generateID();
        ArticleBody articleBody = new ArticleBody();
        articleBody.setId(bodyId);
        articleBody.setArticleId(articleId);
        articleBody.setContent(bodyParam.getContent());
        articleBody.setContentHtml(bodyParam.getContentHtml());
        return articleBody;
    }

    private Article convertToArticle(ArticleParam articleParam, ArticleBody articleBody) {
        SysUserVo userVo = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(Long.parseLong(userVo.getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setCommentCounts(0);
        article.setCategoryId(articleParam.getCategory().getId().intValue());
        article.setWeight(0);
        article.setId(articleBody.getArticleId());
        article.setBodyId(articleBody.getId());
        return article;
    }

    private List<ArticleVo> recordsToList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : records) {
            ArticleVo articleVo = pojoToVo(article, isTag, isAuthor, false, false);
            articleVos.add(articleVo);
        }
        return articleVos;
    }

    // 不是所有的article都需要tag和author，因此加入两个is参数
    private ArticleVo pojoToVo(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = null;
        String redisKey = RedisUtil.getArticleVo(article.getId(), isTag, isAuthor, isBody, isCategory);
        String redisValue = (String) redisTemplate.opsForValue().get(redisKey);
        if (null == redisValue) {
            synchronized (this) {
                redisValue = (String) redisTemplate.opsForValue().get(redisKey);
                if (null == redisValue) {
                    articleVo = new ArticleVo();
                    BeanUtils.copyProperties(article, articleVo);
                    Long articleId = article.getId();
                    articleVo.setId(String.valueOf(articleId));
                    Long createDate = article.getCreateDate();
                    if (createDate != null) {
                        articleVo.setCreateDate(new DateTime(createDate).toString("yyyy-MM-dd HH:mm:ss"));
                    }
                    if (isTag) {
                        articleVo.setTags(tagService.findTagsByArticleId(articleId));
                    }
                    if (isAuthor) {
                        Long authorId = article.getAuthorId();
                        SysUserVo userVo = sysUserService.findSysUser(authorId);
                        articleVo.setAuthor(userVo.getNickname());
                    }
                    if (isBody) {
                        Long bodyId = article.getBodyId();
                        ArticleBody articleBody = articleBodyService.findArticleBodyByBodyId(bodyId);
                        if (null != articleBody) {
                            ArticleBodyVo articleBodyVo = new ArticleBodyVo(articleBody);
                            articleVo.setBody(articleBodyVo);
                        }
                    }
                    if (isCategory) {
                        Long categoryId = article.getCategoryId().longValue();
                        Category category = categoryService.findCategoryById(categoryId);
                        if (null != category) {
                            CategoryVo categoryVo = new CategoryVo(category);
                            articleVo.setCategory(categoryVo);
//                List<CategoryVo> categoryVos = new ArrayList<>();
//                categoryVos.add(categoryVo);
//                articleVo.setCategories(categoryVos);
                        }
                    }
                    redisValue = JSONUtil.getInstance().toJSON(articleVo);
                    redisTemplate.opsForValue().set(redisKey, redisValue, articleRedisExpireTime, TimeUnit.SECONDS);
                }
            }
        }
        if (articleVo == null && redisValue != null) {
            articleVo = JSONUtil.getInstance().toObject(redisValue, ArticleVo.class);
        }
        articleVo.setViewCounts(articleViewCountsService.getArticleViewCounts(article.getId()));
        return articleVo;
    }

    private Article getArticleById(Long articleId) {
        Article article = null;
        String redisKey = RedisUtil.getArticleAbstract(articleId);
        String redisValue = (String) redisTemplate.opsForValue().get(redisKey);
        if (null == redisValue) {
            synchronized (this) {
                redisValue = (String) redisTemplate.opsForValue().get(redisKey);
                if (null == redisValue) {
                    article = articleMapper.selectById(articleId);
                    redisValue = JSONUtil.getInstance().toJSON(article);
                    redisTemplate.opsForValue().set(redisKey, redisValue, articleRedisExpireTime, TimeUnit.SECONDS);
                }
            }
        }
        if (article == null && redisValue != null) {
            article = JSONUtil.getInstance().toObject(redisValue, Article.class);
        }
        return article;
    }

    private ArticleVo getArticleVo(Long articleId, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        Article article = this.getArticleById(articleId);
        return pojoToVo(article, isTag, isAuthor, isBody, isCategory);
    }
}




