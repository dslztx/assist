package me.dslztx.assist.util.filetype;

import me.dslztx.assist.bean.filetype.FileType;

public interface FileTypeMatcher {

    boolean match(byte[] data);

    FileType obtainFileType();
}
