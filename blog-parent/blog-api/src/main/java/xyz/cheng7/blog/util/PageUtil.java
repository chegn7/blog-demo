package xyz.cheng7.blog.util;

import xyz.cheng7.blog.vo.params.PageParams;

public class PageUtil {
    public static int[] convertPageParamToSQLLimit(PageParams pageParams, int count) {
        int page = pageParams.getPage();
        int pageSize = pageParams.getPageSize();
        if (pageSize <= 0 || page <= 0) return null;
        int offset = pageSize * (page - 1);
        if (offset >= count) return null;
        int[] limits = new int[2];
        limits[0] = offset;
        limits[1] = Math.min(pageSize, count - offset);
        return limits;
    }
}
