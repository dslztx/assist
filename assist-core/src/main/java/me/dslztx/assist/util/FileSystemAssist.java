package me.dslztx.assist.util;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemAssist {

    /**
     * 比如获得文件系统ceph
     */
    public static String obtainFileSystemType(String path) {
        try {
            FileStore fs = Files.getFileStore(Paths.get(path));
            return fs.type();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(obtainFileSystemType(args[0]));
    }
}
