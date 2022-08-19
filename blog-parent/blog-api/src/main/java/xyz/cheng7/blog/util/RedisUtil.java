package xyz.cheng7.blog.util;

public class RedisUtil {
    private static final String SPLIT = "::";

    public static String getJWTKey(String jwt) {
        return "jwt" + SPLIT + jwt;
    }

    public static String getCacheKey(String cacheName, String className, String methodName, String params) {
        return String.join(SPLIT, cacheName, className, methodName, params);
    }

    public static String getHotArticleIds(int limit) {
        return String.join(SPLIT, "article", "hotIds", String.valueOf(limit));
    }

    public static String getArticleAbstract(Long articleId) {
        return String.join(SPLIT, "article", "abstract", articleId.toString());
    }

    public static String getArticleViewCount(Long articleId) {
        if (null == articleId) return String.join(SPLIT, "article", "viewCount");
        return String.join(SPLIT, "article", "viewCount", articleId.toString());
    }

    public static String getArticleVo(Long articleId, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        return String.join(SPLIT, "article", "vo", articleId.toString(), String.valueOf(isTag), String.valueOf(isAuthor), String.valueOf(isBody), String.valueOf(isCategory));
    }

    public static String getArticleCategory(Long articleId) {
        return String.join(SPLIT, "article", "category", articleId.toString());
    }

    public static String getArticleTags(Long articleId) {
        return String.join(SPLIT, "article", "tags", articleId.toString());
    }

    public static String getNewArticleIds() {
        return String.join(SPLIT, "article", "new", "ids");
    }

    /**
     * 文章的评论列表是一个zset，zset(commentId, createTime)
     * 二级评论依附于一级评论，因此 这里放的是一级评论的id
     * @param articleId
     * @return
     */
    public static String getArticleCommentIds(Long articleId) {
        return String.join(SPLIT, "article", "commentIds", articleId.toString());
    }

    public static String getSplit() {
        return SPLIT;
    }
}
