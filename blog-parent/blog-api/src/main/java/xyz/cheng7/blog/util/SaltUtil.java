package xyz.cheng7.blog.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class SaltUtil {
    static char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*(),.<>".toCharArray();
    static int charsLen = chars.length;
    public static String getSalt() {
        return getSalt(8);
    }

    public static String getSalt(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int idx = new Random().nextInt(charsLen);
            sb.append(chars[idx]);
        }
        return sb.toString();
    }

    public static String getPasswordWithSalt(String password, String salt) {
        return DigestUtils.md5Hex(password + salt);
    }
}
