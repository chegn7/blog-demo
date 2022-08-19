import org.junit.jupiter.api.Test;
import xyz.cheng7.blog.util.TimeUtil;

class TimeUtilTest {

    @Test
    void longToString() {

        System.out.println(TimeUtil.longToString(1231231231230L));
    }

    @Test
    void testStringToDateTime() {
        System.out.println(TimeUtil.stringToDate("2021", "1"));
    }
}