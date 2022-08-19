package xyz.cheng7.blog.vo;

public enum ErrorCode {

    PARAMS_ERROR(10001, "参数错误"),
    ACCOUNT_PWD_NOT_EXIST(20001, "用户名或密码错误"),
    USER_NOT_FOUND(20002, "未找到用户"),
    ACCOUNT_ALREADY_EXIST(20003, "用户已存在，请登录"),
    NOT_LOGIN_ERROR(30001, "未登录"),
    PERMISSION_ERROR(40001, "无权限"),
    TIME_OUT_ERROR(50001, "超时"),
    ARTICLE_GET_ERROR(60001, "文章获取错误"),
    ARTICLE_PUBLISH_ERROR(60002, "文章发布失败"),
    COMMENT_GET_ERROR(70001, "评论获取错误"),
    COMMENT_CREATE_ERROR(70002, "评论创建失败"),
    UNSUPPORTED_FILE_FORMAT(80001, "文件格式不支持"),
    FILE_UPLOAD_FAILURE(80002, "文件上传失败"),
    OTHER_ERROR(00000, "其他错误");



    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
