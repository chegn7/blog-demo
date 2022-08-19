package xyz.cheng7.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.cheng7.blog.service.ArticleScoreService;

import java.util.List;

@SpringBootTest
public class DBTest {
    @Autowired
    ArticleScoreService service;

    @Test
    public void test1() {
        int cnt = 18;
        List<Long> hotIds = service.getHotIds(cnt);
        System.out.println(hotIds);
        for (Long hotId : hotIds) {
            service.updateByArticleId(hotId);
        }
        System.out.println("更新完成");

    }
}
