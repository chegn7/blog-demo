package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cheng7.blog.dao.pojo.ArticleTag;
import xyz.cheng7.blog.service.ArticleTagService;
import xyz.cheng7.blog.dao.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author c
* @description 针对表【ms_article_tag】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleTag> findArticleTagByTagId(Long tagId) {
        return articleTagMapper.selectByTagId(tagId);
    }
}




