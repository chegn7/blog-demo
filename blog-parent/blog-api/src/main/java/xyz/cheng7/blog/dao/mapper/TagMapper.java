package xyz.cheng7.blog.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.cheng7.blog.dao.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.cheng7.blog.vo.TagVo;

import java.util.List;

/**
* @author c
* @description 针对表【ms_tag】的数据库操作Mapper
* @createDate 2022-08-08 15:02:19
* @Entity xyz.cheng7.blog.dao.pojo.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id，查找标签列表
     * 先在 article_tag表中，根据article_id 查找 tag_id
     * 再在 tag表中，根据tag_id 查找 tag的信息
     * @param articleId
     * @return tag列表
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 按limit数量查找最热标签的id列表
     * @param limit
     * @return
     */
    List<Long> findHotTagIds(int limit);

    List<Tag> selectListByIds(List<Long> hotTagIds);

    List<Tag> selectAllTags();

    TagVo findTagByTagId(Long tagId);
}




