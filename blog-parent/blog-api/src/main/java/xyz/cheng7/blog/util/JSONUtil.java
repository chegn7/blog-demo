package xyz.cheng7.blog.util;

import com.google.gson.Gson;

public class JSONUtil {
    private static Gson gson;
    private static JSONUtil jsonUtil = new JSONUtil();

    private JSONUtil() {
        gson = new Gson();
    }

    public static JSONUtil getInstance() {
        return jsonUtil;
    }

    public <T> T  toObject(String s, Class<T> clazz) {
        return gson.fromJson(s, clazz);
    }

    public String toJSON(Object o) {
        return gson.toJson(o);
    }


}
