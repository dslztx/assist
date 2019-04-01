package me.dslztx.assist.util.filetype;

import me.dslztx.assist.bean.filetype.FileType;
import me.dslztx.assist.util.ArrayAssist;

public class BMPMatcher implements FileTypeMatcher {

    @Override
    public boolean match(byte[] data) {
        if (ArrayAssist.isEmpty(data)) {
            return false;
        }

        if (data.length >= 2) {
            if (data[0] == (byte)0x42 && data[1] == (byte)0x4d) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FileType obtainFileType() {
        return FileType.BMP;
    }
}
