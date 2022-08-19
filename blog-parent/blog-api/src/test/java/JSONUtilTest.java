import org.junit.jupiter.api.Test;
import xyz.cheng7.blog.util.JSONUtil;
import xyz.cheng7.blog.vo.params.LoginParams;

import static org.junit.jupiter.api.Assertions.*;

class JSONUtilTest {

    @Test
    void getInstance() {
        System.out.println(JSONUtil.getInstance());
    }

    @Test
    void toObject() {
        JSONUtil jsonUtil = JSONUtil.getInstance();
        String s = jsonUtil.toJSON(new LoginParams("acc", "pwd", "jwt"));
        LoginParams loginParams = jsonUtil.toObject(s, LoginParams.class);
        System.out.println(loginParams.toString());
    }

    @Test
    void toJSON() {
        String s = JSONUtil.getInstance().toJSON(new LoginParams("acc", "pwd", "jwt"));
        System.out.println(s);
    }
}