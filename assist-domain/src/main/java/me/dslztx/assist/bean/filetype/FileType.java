package me.dslztx.assist.bean.filetype;

import java.util.HashSet;
import java.util.Set;

/**
 * 文件类型，不断扩充
 */
public enum FileType {
    JPEG, BMP, GIF, TIFF, PSD, PNG, EXIF, ICO, SVG, EPS, WEBP,

    DOC, DOCX, XLS, PDF,

    UNKNOWN, NOT_IMAGE;

    private static Set<String> set = null;

    static {
        FileType[] fileTypes = values();

        set = new HashSet<String>((int)(fileTypes.length / 0.75) + 1);

        for (FileType fileType : fileTypes) {
            set.add(fileType.toString());
        }
    }

    public static boolean contains(String value) {
        // try{
        // valueOf(value);
        // return true;
        // }catch (IllegalArgumentException e){
        // return false;
        // }

        // 以上实现方式需要抛出异常，不优雅，换成如下：

        return set.contains(value);
    }

    public static boolean contains(FileType fileType) {
        FileType[] fileTypes = values();

        for (FileType v : fileTypes) {
            if (v == fileType) {
                return true;
            }
        }

        return false;
    }
}
