package xyz.cheng7.blog.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import xyz.cheng7.blog.dao.pojo.Category;

@Data
@NoArgsConstructor
public class CategoryVo {
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;

    public CategoryVo(Category category) {
        BeanUtils.copyProperties(category, this);
    }
}
