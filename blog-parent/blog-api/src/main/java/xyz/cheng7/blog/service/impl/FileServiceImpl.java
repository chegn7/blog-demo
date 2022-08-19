package xyz.cheng7.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cheng7.blog.service.FileService;
import xyz.cheng7.blog.util.QiNiuUtil;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private QiNiuUtil qiNiuUtil;

    @Override
    public String upload(MultipartFile file, String fileName) {
        return qiNiuUtil.uploadFile(file, fileName);
    }
}
