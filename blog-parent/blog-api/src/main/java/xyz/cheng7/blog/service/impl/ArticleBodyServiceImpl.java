package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cheng7.blog.dao.pojo.ArticleBody;
import xyz.cheng7.blog.service.ArticleBodyService;
import xyz.cheng7.blog.dao.mapper.ArticleBodyMapper;
import org.springframework.stereotype.Service;

/**
* @author c
* @description 针对表【ms_article_body】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody>
    implements ArticleBodyService{
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBody findArticleBodyByBodyId(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectArticleBodyById(bodyId);
        return articleBody;
    }

    @Override
    public int insertArticleBody(ArticleBody articleBody) {
        return articleBodyMapper.insert(articleBody);
    }
}




