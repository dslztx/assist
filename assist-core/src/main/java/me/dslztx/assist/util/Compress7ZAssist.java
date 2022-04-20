package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class Compress7ZAssist {

    private static final Logger logger = LoggerFactory.getLogger(Compress7ZAssist.class);

    public static void compress(File input, File output) {
        SevenZOutputFile sevenZOutput = null;
        try {
            if (input == null || !input.exists()) {
                throw new RuntimeException("待压缩文件不存在");
            }

            if (output == null) {
                output = obtainDefaultOutput(input);
            }

            if (input.isFile()) {
                sevenZOutput = new SevenZOutputFile(output);

                addEntry(input, input.getName(), sevenZOutput);
            } else {
                sevenZOutput = new SevenZOutputFile(output);
                if (ArrayAssist.isEmpty(input.listFiles())) {
                    addEntry(input, input.getName(), sevenZOutput);
                } else {
                    compressSubDirElements(input.listFiles(), input.getName(), sevenZOutput);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(sevenZOutput);
        }
    }

    public static void decompress(File input, File output) {
        if (!FileAssist.isRegular(input)) {
            throw new RuntimeException("待解压压缩文件不合法");
        }

        if (!FileAssist.isDir(output)) {
            throw new RuntimeException("未指定合法的解压后存放路径");
        }

        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(input);

            SevenZArchiveEntry entry = null;

            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(output.getCanonicalPath() + File.separator + entry.getName()).mkdirs();
                } else {
                    File target = new File(output.getCanonicalPath() + File.separator + entry.getName());

                    target.getParentFile().mkdirs();

                    BufferedOutputStream out = IOAssist.bufferedOutputStream(target);

                    byte[] buffer = new byte[1024];
                    long num = entry.getSize() / 1024 + 1;
                    for (long index = 0; index < num - 1; index++) {
                        sevenZFile.read(buffer, 0, 1024);
                        out.write(buffer, 0, 1024);
                    }

                    int left = (int)(entry.getSize() - (num - 1) * 1024);
                    if (left > 0) {
                        sevenZFile.read(buffer, 0, left);
                        out.write(buffer, 0, left);
                    }

                    CloseableAssist.close(out);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(sevenZFile);
        }
    }

    private static void addEntry(File file, String entryName, SevenZOutputFile sevenZOutputFile) throws IOException {
        SevenZArchiveEntry entry = sevenZOutputFile.createArchiveEntry(file, entryName);
        sevenZOutputFile.putArchiveEntry(entry);

        if (!file.isDirectory()) {
            BufferedInputStream in = IOAssist.bufferedInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) > 0) {
                sevenZOutputFile.write(bytes, 0, len);
            }
            CloseableAssist.close(in);
        }

        sevenZOutputFile.closeArchiveEntry();
    }

    private static void compressSubDirElements(File[] files, String prefix, SevenZOutputFile sevenZOutputFile)
        throws IOException {
        for (File file : files) {
            if (file.isFile()) {
                addEntry(file, prefix + File.separator + file.getName(), sevenZOutputFile);
            } else {
                if (ArrayAssist.isEmpty(file.listFiles())) {
                    addEntry(file, prefix + File.separator + file.getName(), sevenZOutputFile);
                } else {
                    compressSubDirElements(file.listFiles(), prefix + File.separator + file.getName(),
                        sevenZOutputFile);
                }
            }
        }
    }

    private static File obtainDefaultOutput(File input) throws IOException {
        return new File(input.getCanonicalPath() + ".7z");
    }
}