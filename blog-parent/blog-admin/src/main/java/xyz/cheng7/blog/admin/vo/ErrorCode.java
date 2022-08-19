package xyz.cheng7.blog.admin.vo;

public enum ErrorCode {
    CREATE_PERMISSION_FAILURE(10001, "创建权限失败"),
    DELETE_PERMISSION_FAILURE(10002, "删除权限失败"),
    UPDATE_PERMISSION_FAILURE(10003, "更新权限失败"),
    RETRIEVE_PERMISSION_FAILURE(10004, "检索权限失败");
    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
