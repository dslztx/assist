package me.dslztx.assist.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.bean.filetype.FileType;
import me.dslztx.assist.util.filetype.BMPMatcher;
import me.dslztx.assist.util.filetype.FileTypeMatcher;
import me.dslztx.assist.util.filetype.GIFMatcher;
import me.dslztx.assist.util.filetype.JPEGMatcher;
import me.dslztx.assist.util.filetype.PNGMatcher;
import me.dslztx.assist.util.filetype.PSDMatcher;
import me.dslztx.assist.util.filetype.TIFFMatcher;

public class FileTypeAssist {

    public static final Logger logger = LoggerFactory.getLogger(FileTypeAssist.class);

    private static final Map<String, FileType> imageExts = new HashMap<String, FileType>();

    private static List<FileTypeMatcher> imageMatchChains = new ArrayList<FileTypeMatcher>();

    static {
        initImageExts();

        initImageMatchChains();
    }

    private static void initImageMatchChains() {
        imageMatchChains.add(new PNGMatcher());
        imageMatchChains.add(new BMPMatcher());
        imageMatchChains.add(new JPEGMatcher());
        imageMatchChains.add(new TIFFMatcher());
        imageMatchChains.add(new PSDMatcher());
        imageMatchChains.add(new GIFMatcher());
    }

    private static void initImageExts() {
        imageExts.put("jpg", FileType.JPEG);
        imageExts.put("jpeg", FileType.JPEG);
        imageExts.put("jpe", FileType.JPEG);

        imageExts.put("bmp", FileType.BMP);

        imageExts.put("gif", FileType.GIF);

        imageExts.put("tiff", FileType.TIFF);
        imageExts.put("tif", FileType.TIFF);

        imageExts.put("psd", FileType.PSD);

        imageExts.put("png", FileType.PNG);

        imageExts.put("exif", FileType.EXIF);

        imageExts.put("ico", FileType.ICO);
        imageExts.put("svg", FileType.SVG);
        imageExts.put("eps", FileType.EPS);
        imageExts.put("webp", FileType.WEBP);
    }

    public static FileType recognizeImage(byte[] data) {
        if (ArrayAssist.isEmpty(data)) {
            return FileType.NOT_IMAGE;
        }

        for (FileTypeMatcher matcher : imageMatchChains) {
            if (matcher.match(data)) {
                return matcher.obtainFileType();
            }
        }

        return FileType.NOT_IMAGE;
    }

    public static FileType recognizeImage(String name) {
        String ext = FilenameAssist.obtainExt(name);

        if (StringAssist.isBlank(ext)) {
            return FileType.NOT_IMAGE;
        }

        ext = ext.toLowerCase();

        if (ObjectAssist.isNull(imageExts.get(ext))) {
            return FileType.NOT_IMAGE;
        }

        return imageExts.get(ext);
    }
}
