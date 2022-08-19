package xyz.cheng7.blog.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
public class FileUtil {
    public static Set<String> supportedImageExtensions;
    static {
        supportedImageExtensions = new HashSet<>();
        supportedImageExtensions.add("jpg");
        supportedImageExtensions.add("png");
    }

    public static String getExtension(String originalFilename) {
//        String[] ss = originalFilename.split("\\.");
//        return ss[ss.length - 1].toLowerCase();
        String extension = StringUtils.substringAfterLast(originalFilename, ".");
        return extension.toLowerCase();
    }
}
