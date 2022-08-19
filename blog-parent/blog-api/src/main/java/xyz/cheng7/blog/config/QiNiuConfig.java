package xyz.cheng7.blog.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QiNiuConfig {

    @Value("${qiniu.kodo.key.accesskey}")
    private String accessKey;

    @Value("${qiniu.kodo.key.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public UploadManager getUploadManager() {
        // 空间是华东区的
        return new UploadManager(new com.qiniu.storage.Configuration(Region.huadong()));
    }
}
