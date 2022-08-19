package xyz.cheng7.blog.util;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class QiNiuUtil {

    @Autowired
    private Auth auth;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private IDUtil idUtil;

    @Value("${qiniu.kodo.bucket}")
    private String bucket;

    @Value("${qiniu.kodo.domain}")
    private String domain;

    public String uploadFile(MultipartFile file) {
        String fileName = idUtil.generateID() + "." + FileUtil.getExtension(file.getOriginalFilename());
        return uploadFile(file, fileName);
    }

    public String uploadFile(MultipartFile file, String fileName) {
        try {
            byte[] fileBytes = file.getBytes();
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(fileBytes, fileName, upToken);
            DefaultPutRet putRet = JSONUtil.getInstance().toObject(response.bodyString(), DefaultPutRet.class);
            return domain + "/" + putRet.key;
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败");
        }
    }
}
