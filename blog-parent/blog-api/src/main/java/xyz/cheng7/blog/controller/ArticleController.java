package xyz.cheng7.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import xyz.cheng7.blog.common.aop.LogAnnotation;
import xyz.cheng7.blog.dao.dos.Archives;
import xyz.cheng7.blog.service.ArticleService;
import xyz.cheng7.blog.vo.ArticleVo;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;
import xyz.cheng7.blog.vo.params.ArticleParam;
import xyz.cheng7.blog.vo.params.PageParams;

import java.util.List;

@RestController // json数据交互
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module="article", operation="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams) {
        List<ArticleVo> articleVos = articleService.listArticle(pageParams);
        if (articleVos == null) return Result.failure(ErrorCode.ARTICLE_GET_ERROR.getCode(), ErrorCode.ARTICLE_GET_ERROR.getMsg());
        return Result.success(articleVos);
    }

//    @Cache(expireTimeMills = 5 * 60 * 1000, name = "hot article")
    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        List<ArticleVo> articleVos = articleService.hotArticle(limit);
        if (CollectionUtils.isEmpty(articleVos)) return Result.failure(ErrorCode.ARTICLE_GET_ERROR.getCode(), ErrorCode.ARTICLE_GET_ERROR.getMsg());
        return Result.success(articleVos);
    }

    @PostMapping("new")
    public Result newArticle() {
        int limit = 5;
        List<ArticleVo> articleVos = articleService.newArticle(limit);
        if (CollectionUtils.isEmpty(articleVos)) return Result.failure(ErrorCode.ARTICLE_GET_ERROR.getCode(), ErrorCode.ARTICLE_GET_ERROR.getMsg());
        return Result.success(articleVos);
    }

    @PostMapping("listArchives")
    public Result listArchives() {
        List<Archives> archivesList = articleService.listArchives();
        if (CollectionUtils.isEmpty(archivesList)) return Result.failure(ErrorCode.ARTICLE_GET_ERROR.getCode(), ErrorCode.ARTICLE_GET_ERROR.getMsg());
        return Result.success(archivesList);
    }

    @LogAnnotation(module = "articles", operation = "view article")
    @PostMapping("view/{id}")
    public Result view(@PathVariable(value = "id")Long articleId) {
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        ArticleVo articleVo = articleService.publish(articleParam);
        if (null == articleVo) return Result.failure(ErrorCode.ARTICLE_PUBLISH_ERROR.getCode(), ErrorCode.ARTICLE_PUBLISH_ERROR.getMsg());
        return Result.success(articleVo);
    }
}
