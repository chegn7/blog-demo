import org.junit.jupiter.api.Test;
import xyz.cheng7.blog.util.JWTUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilTest {
    Long userId = 1001L;

    @Test
    void createToken() {
        String jwt = JWTUtil.createToken(userId);
        System.out.println(jwt);
    }

    @Test
    void checkToken() {
        String jwt = JWTUtil.createToken(userId);
        Map<String, Object> tokenBody = JWTUtil.checkToken(jwt);
        System.out.println(tokenBody);
    }
}