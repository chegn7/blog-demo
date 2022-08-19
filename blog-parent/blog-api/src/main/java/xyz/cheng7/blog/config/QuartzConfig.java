package xyz.cheng7.blog.config;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import xyz.cheng7.blog.quartz.UpdateArticleScore;
import xyz.cheng7.blog.quartz.UpdateViewCount2DB;

// 配置 -> 数据库 -> 调用
@Configuration
public class QuartzConfig {
    private static long UPDATE_VIEW_COUNTS_INTERVAL = 1 * 1000 * 10;
    private static long UPDATE_ARTICLE_SCORE_INTERVAL = 1 * 1000 * 10;


    // FactoryBean 可简化Bean的实例化过程
    // 1. 通过 FactoryBean 封装Bean的实例化过程
    // 2. 将 FactoryBean 装配到Spring容器里
    // 3. 将 FactoryBean 注入给其他的Bean
    // 4. 该 Bean 得到 FactoryBean 管理的实例对象

    //    @Bean
//    public JobDetailFactoryBean alphaJobDetail() {
//        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
//        factoryBean.setJobClass(AlphaJob.class);
//        factoryBean.setName("alphaJob");
//        factoryBean.setGroup("alphaJobGroup");
//        factoryBean.setDurability(true);
//        factoryBean.setRequestsRecovery(true);
//        return factoryBean;
//    }

    //    CronTriggerFactoryBean 复杂定时任务
//    @Bean
//    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(alphaJobDetail);
//        factoryBean.setName("alphaTrigger");
//        factoryBean.setGroup("alphaTriggerGroup");
//        factoryBean.setRepeatInterval(3000);
//        factoryBean.setJobDataMap(new JobDataMap());
//
//        return factoryBean;
//    }
    @Bean
    public JobDetailFactoryBean updateViewCountJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(UpdateViewCount2DB.class);
        factoryBean.setName("updateViewCountJob");
        factoryBean.setGroup("blogJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean updateViewCountTrigger(JobDetail updateViewCountJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(updateViewCountJobDetail);
        factoryBean.setName("updateViewCountTrigger");
        factoryBean.setGroup("blogJobGroup");
        factoryBean.setJobDataMap(new JobDataMap());
        factoryBean.setRepeatInterval(UPDATE_VIEW_COUNTS_INTERVAL);
        return factoryBean;
    }

    //    CronTriggerFactoryBean 复杂定时任务

    @Bean
    public JobDetailFactoryBean updateArticleScoreDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(UpdateArticleScore.class);
        factoryBean.setName("updateArticleScoreJob");
        factoryBean.setGroup("blogJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean updateArticleScoreTrigger(JobDetail updateArticleScoreDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(updateArticleScoreDetail);
        factoryBean.setName("updateArticleScoreTrigger");
        factoryBean.setGroup("blogJobGroup");
        factoryBean.setJobDataMap(new JobDataMap());
        factoryBean.setRepeatInterval(UPDATE_ARTICLE_SCORE_INTERVAL);
        return factoryBean;
    }
}
