package me.dslztx.assist.util.filetype;

import me.dslztx.assist.bean.filetype.FileType;
import me.dslztx.assist.util.ArrayAssist;

public class JPEGMatcher implements FileTypeMatcher {

    @Override
    public boolean match(byte[] data) {
        if (ArrayAssist.isEmpty(data)) {
            return false;
        }

        if (data.length >= 4) {
            if (data[0] == (byte)0xff && data[1] == (byte)0xd8 && data[data.length - 1] == (byte)0xd9
                && data[data.length - 2] == (byte)0xff) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FileType obtainFileType() {
        return FileType.JPEG;
    }
}
