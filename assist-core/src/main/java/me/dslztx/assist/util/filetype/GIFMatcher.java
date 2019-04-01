package me.dslztx.assist.util.filetype;

import me.dslztx.assist.bean.filetype.FileType;
import me.dslztx.assist.util.ArrayAssist;

public class GIFMatcher implements FileTypeMatcher {

    @Override
    public boolean match(byte[] data) {
        if (ArrayAssist.isEmpty(data)) {
            return false;
        }

        if (data.length >= 6) {
            if (data[0] == (byte)0x47 && data[1] == (byte)0x49 && data[2] == (byte)0x46 && data[3] == (byte)0x38
                && (data[4] == (byte)0x39 || data[4] == (byte)0x37) && data[5] == 0x61) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FileType obtainFileType() {
        return FileType.GIF;
    }
}
