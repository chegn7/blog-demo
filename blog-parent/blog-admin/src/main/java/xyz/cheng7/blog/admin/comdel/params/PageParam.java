package xyz.cheng7.blog.admin.comdel.params;

import lombok.Data;

@Data
public class PageParam {
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;
    private Integer total;
}
