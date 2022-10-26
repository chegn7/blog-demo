package xyz.cheng7.blog.util;

import xyz.cheng7.blog.vo.SysUserVo;

public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUserVo> SYS_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void put(SysUserVo userVo) {
        SYS_USER_THREAD_LOCAL.set(userVo);
    }

    public static SysUserVo get() {
        SysUserVo userVo = SYS_USER_THREAD_LOCAL.get();
        return userVo;
    }

    public static void remove() {
        SYS_USER_THREAD_LOCAL.remove();
    }
}
