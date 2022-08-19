package xyz.cheng7.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.cheng7.blog.dao.pojo.ArticleTag;
import xyz.cheng7.blog.dao.pojo.Tag;
import xyz.cheng7.blog.vo.TagVo;

import java.util.List;

/**
* @author c
* @description 针对表【ms_tag】的数据库操作Service
* @createDate 2022-08-08 15:02:20
*/
public interface TagService extends IService<Tag> {
    public List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 最热标签，统计各个标签的文章数，降序，最多的文章数即最热标签
     * @param limit
     * @return
     */
    List<TagVo> getHotTags(int limit);

    List<TagVo> findAllTags();

    Tag convertToTag(TagVo tagVo);

    Integer insertArticleTag(ArticleTag articleTag);

    TagVo findTagByTagId(Long tagId);
}
