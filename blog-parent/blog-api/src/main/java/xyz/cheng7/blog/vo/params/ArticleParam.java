package xyz.cheng7.blog.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.cheng7.blog.vo.CategoryVo;
import xyz.cheng7.blog.vo.TagVo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleParam {

    private String title;

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

}
