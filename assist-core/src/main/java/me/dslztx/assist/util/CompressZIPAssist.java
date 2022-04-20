package me.dslztx.assist.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class CompressZIPAssist {

    private static final Logger logger = LoggerFactory.getLogger(CompressZIPAssist.class);

    public static void compress(File input, File output) {
        ZipOutputStream zipOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (input == null || !input.exists()) {
                throw new RuntimeException("待压缩文件不存在");
            }

            if (output == null) {
                output = obtainDefaultOutput(input);
            }

            if (output.isDirectory()) {
                throw new RuntimeException("目标文件不能是目录");
            }

            fileOutputStream = new FileOutputStream(output);
            zipOutputStream = new ZipOutputStream(fileOutputStream);

            if (input.isFile()) {
                addEntry(input, input.getName(), zipOutputStream);
            } else {
                if (ArrayAssist.isEmpty(input.listFiles())) {
                    addEntry(input, input.getName(), zipOutputStream);
                } else {
                    compressSubDirElements(input.listFiles(), input.getName(), zipOutputStream);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(zipOutputStream);
            CloseableAssist.close(fileOutputStream);
        }
    }

    public static void compress(File input, ByteArrayOutputStream byteArrayOutputStream) {
        ZipOutputStream zipOutputStream = null;
        try {
            if (input == null || !input.exists()) {
                throw new RuntimeException("待压缩文件不存在");
            }

            if (byteArrayOutputStream == null) {
                throw new RuntimeException("未指定压缩输出流");
            }

            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

            if (input.isFile()) {
                addEntry(input, input.getName(), zipOutputStream);
            } else {
                if (ArrayAssist.isEmpty(input.listFiles())) {
                    addEntry(input, input.getName(), zipOutputStream);
                } else {
                    compressSubDirElements(input.listFiles(), input.getName(), zipOutputStream);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(zipOutputStream);
        }
    }

    public static void decompress(File input, File output) {
        if (!FileAssist.isRegular(input)) {
            throw new RuntimeException("待解压压缩文件不合法");
        }

        if (!FileAssist.isDir(output)) {
            throw new RuntimeException("未指定合法的解压后存放路径");
        }

        ZipInputStream zipInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(input);
            zipInputStream = new ZipInputStream(fileInputStream);

            ZipEntry entry = null;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(output.getCanonicalPath() + File.separator + entry.getName()).mkdirs();
                } else {
                    File target = new File(output.getCanonicalPath() + File.separator + entry.getName());

                    target.getParentFile().mkdirs();

                    BufferedOutputStream out = IOAssist.bufferedOutputStream(target);

                    byte[] buffer = new byte[1024];
                    int cnt;
                    while ((cnt = zipInputStream.read(buffer, 0, 1024)) > 0) {
                        out.write(buffer, 0, cnt);
                    }

                    CloseableAssist.close(out);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(zipInputStream);
            CloseableAssist.close(fileInputStream);
        }
    }

    public static void decompress(ByteArrayInputStream byteArrayInputStream, File output) {
        if (byteArrayInputStream == null) {
            throw new RuntimeException("未指定解压输入流");
        }

        if (!FileAssist.isDir(output)) {
            throw new RuntimeException("未指定合法的解压后存放路径");
        }

        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(byteArrayInputStream);

            ZipEntry entry = null;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(output.getCanonicalPath() + File.separator + entry.getName()).mkdirs();
                } else {
                    File target = new File(output.getCanonicalPath() + File.separator + entry.getName());

                    target.getParentFile().mkdirs();

                    BufferedOutputStream out = IOAssist.bufferedOutputStream(target);

                    byte[] buffer = new byte[1024];
                    int cnt;
                    while ((cnt = zipInputStream.read(buffer, 0, 1024)) > 0) {
                        out.write(buffer, 0, cnt);
                    }

                    CloseableAssist.close(out);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.close(zipInputStream);
        }
    }

    private static void addEntry(File file, String entryName, ZipOutputStream zipOutputStream) throws IOException {
        ZipEntry zipEntry = null;
        if (file.isDirectory()) {
            zipEntry = new ZipEntry(entryName + File.separator);
        } else {
            zipEntry = new ZipEntry(entryName);
        }
        zipOutputStream.putNextEntry(zipEntry);

        if (!file.isDirectory()) {
            BufferedInputStream in = IOAssist.bufferedInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) > 0) {
                zipOutputStream.write(bytes, 0, len);
            }
            CloseableAssist.close(in);
        }
    }

    private static void compressSubDirElements(File[] files, String prefix, ZipOutputStream zipOutputStream)
        throws IOException {
        for (File file : files) {
            if (file.isFile()) {
                addEntry(file, prefix + File.separator + file.getName(), zipOutputStream);
            } else {
                if (ArrayAssist.isEmpty(file.listFiles())) {
                    addEntry(file, prefix + File.separator + file.getName(), zipOutputStream);
                } else {
                    compressSubDirElements(file.listFiles(), prefix + File.separator + file.getName(), zipOutputStream);
                }
            }
        }
    }

    private static File obtainDefaultOutput(File input) throws IOException {
        return new File(input.getCanonicalPath() + ".zip");
    }
}