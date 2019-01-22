package me.dslztx.assist.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.bean.FileFormatEnum;

public class FileFormatAssist {

    public static final Logger logger = LoggerFactory.getLogger(FileFormatAssist.class);

    private static final Set<String> imageExts = new HashSet<String>(16);

    static {
        initImageExts();
    }

    private static void initImageExts() {
        String imageExtensions =
            "jpg|jpeg|jpe|bmp|gif|tiff|tif|psd|png|pcx|tga|exif|ico|svg|fpx|cdr|pcd|dxf|ufo|eps|webp|raw";

        String[] ss = StringUtils.split(imageExtensions, '|');
        imageExts.addAll(Arrays.asList(ss));
    }

    public static FileFormatEnum fromExt(String ext) {
        if (StringAssist.isBlank(ext)) {
            throw new RuntimeException("ext is blank");
        }

        ext = trimDotIfExist(ext);

        String extLowerCase = ext.toLowerCase();

        if (imageExts.contains(extLowerCase)) {
            return FileFormatEnum.IMAGE;
        } else {
            return FileFormatEnum.UNKNOWN;
        }
    }

    private static String trimDotIfExist(String ext) {
        int index = ext.lastIndexOf(".");
        if (index != -1) {
            return ext.substring(index + 1);
        } else {
            return ext;
        }
    }
}
