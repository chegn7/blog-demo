package xyz.cheng7.blog.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.cheng7.blog.service.FileService;
import xyz.cheng7.blog.util.FileUtil;
import xyz.cheng7.blog.util.IDUtil;
import xyz.cheng7.blog.vo.ErrorCode;
import xyz.cheng7.blog.vo.Result;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private IDUtil idUtil;

    @Autowired
    private FileService fileService;

    @PostMapping
    public Result uploadImage(@RequestParam("image")MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = FileUtil.getExtension(originalFilename);
        if (!FileUtil.supportedImageExtensions.contains(extension)) {
            return Result.failure(ErrorCode.UNSUPPORTED_FILE_FORMAT.getCode(), ErrorCode.UNSUPPORTED_FILE_FORMAT.getMsg());
        }
        String fileName = idUtil.generateID() + "." + extension;
        String key = fileService.upload(file, fileName);
        if (StringUtils.isBlank(key)) return Result.failure(ErrorCode.FILE_UPLOAD_FAILURE.getCode(), ErrorCode.FILE_UPLOAD_FAILURE.getMsg());
        return Result.success(key);
    }
}
