package xyz.cheng7.blog.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.cheng7.blog.service.ArticleViewCountsService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleViewCountsServiceImplTest {
    @Autowired
    ArticleViewCountsService articleViewCountsService;

    @Test
    void getArticleViewCounts() {
        System.out.println(articleViewCountsService.getArticleViewCounts(1L));
    }

    @Test
    void getHotArticleId() {
        System.out.println(articleViewCountsService.getHotArticleId(5));
    }
}