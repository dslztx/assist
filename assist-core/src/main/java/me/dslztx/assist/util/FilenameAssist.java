package me.dslztx.assist.util;

public class FilenameAssist {

    public static String obtainExt(String name) {
        if (StringAssist.isBlank(name)) {
            return "";
        }

        int index = name.lastIndexOf(".");
        if (index == -1 || index == name.length() - 1) {
            return "";
        }

        return name.substring(index + 1);
    }
}
