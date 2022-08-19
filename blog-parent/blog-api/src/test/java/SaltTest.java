import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class SaltTest {
    @Test
    void saltTest() {
        String pwd = "123456";
        String salt = "mysalt";
        System.out.println(DigestUtils.md5Hex(pwd + salt));
    }
}
