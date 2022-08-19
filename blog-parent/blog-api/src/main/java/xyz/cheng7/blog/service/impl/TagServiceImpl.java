package xyz.cheng7.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.cheng7.blog.dao.mapper.ArticleTagMapper;
import xyz.cheng7.blog.dao.mapper.TagMapper;
import xyz.cheng7.blog.dao.pojo.ArticleTag;
import xyz.cheng7.blog.dao.pojo.Tag;
import xyz.cheng7.blog.service.TagService;
import xyz.cheng7.blog.vo.TagVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author c
* @description 针对表【ms_tag】的数据库操作Service实现
* @createDate 2022-08-08 15:02:19
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // mybatisplus 无法多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        if (tags == null) {
            return new ArrayList<>();
        }
        List<TagVo> tagVos = toVoList(tags);
        return tagVos;
    }

    @Override
    public List<TagVo> getHotTags(int limit) {
        List<Long> hotTagIds = tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(hotTagIds)) {
            return Collections.emptyList();
        }
        // select * from t_tags where id in (...)  in的不能为空
        List<Tag> tagList = tagMapper.selectListByIds(hotTagIds);
        List<TagVo> tagVos = toVoList(tagList);
        return tagVos;
    }

    @Override
    public List<TagVo> findAllTags() {
        List<Tag> tags = tagMapper.selectAllTags();
        if (CollectionUtils.isEmpty(tags)) return Collections.emptyList();
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            TagVo tagVo = convertToTagVo(tag);
            tagVos.add(tagVo);
        }
        return tagVos;
    }

    @Override
    public Tag convertToTag(TagVo tagVo) {
        Tag tag = new Tag();
        tag.setTagName(tagVo.getTagName());
        tag.setId(Long.valueOf(tagVo.getId()));
        return tag;
    }

    @Override
    public Integer insertArticleTag(ArticleTag articleTag) {
        return articleTagMapper.insert(articleTag);
    }

    @Override
    public TagVo findTagByTagId(Long tagId) {

        return tagMapper.findTagByTagId(tagId);
    }

    private TagVo convertToTagVo(Tag tag) {
        if (null == tag) return null;
        TagVo tagVo = new TagVo();
        tagVo.setId(String.valueOf(tag.getId()));
        tagVo.setTagName(tag.getTagName());
        tagVo.setAvatar(tag.getAvatar());
        return tagVo;
    }

    private List<TagVo> toVoList(List<Tag> tags) {
        List<TagVo> tagVos = new ArrayList<>();
        for (Tag tag : tags) {
            TagVo tagVo = pojoToVo(tag);
            tagVos.add(tagVo);
        }
        return tagVos;
    }

    private TagVo pojoToVo(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

}




