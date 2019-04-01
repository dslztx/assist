package me.dslztx.assist.util.filetype;

import me.dslztx.assist.bean.filetype.FileType;
import me.dslztx.assist.util.ArrayAssist;

public class PNGMatcher implements FileTypeMatcher {

    @Override
    public boolean match(byte[] data) {
        if (ArrayAssist.isEmpty(data)) {
            return false;
        }

        if (data.length >= 8) {
            if (data[0] == (byte)0x89 && data[1] == (byte)0x50 && data[2] == (byte)0x4e && data[3] == (byte)0x47
                && data[4] == (byte)0x0D && data[5] == (byte)0x0A && data[6] == (byte)0x1A && data[7] == (byte)0x0A) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FileType obtainFileType() {
        return FileType.PNG;
    }
}
