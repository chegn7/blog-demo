package xyz.cheng7.blog.util;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IDUtilTest extends TestCase {

    @Autowired
    IDUtil idUtil;

    @Test
    public void testGenerateID() {
        for (int i = 0; i < 100; i++) {
            System.out.println(idUtil.generateID());
        }
    }
}