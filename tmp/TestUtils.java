package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtils {

  private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);

  public static void addEntry(File file, String entryName, SevenZOutputFile sevenZOutputFile)
      throws IOException {
    SevenZArchiveEntry entry = sevenZOutputFile.createArchiveEntry(file, entryName);
    sevenZOutputFile.putArchiveEntry(entry);
    BufferedInputStream in = IOUtils.bufferedInputStream(file);
    byte[] bytes = new byte[1024];
    int len = 0;
    while ((len = in.read(bytes)) > 0) {
      sevenZOutputFile.write(bytes, 0, len);
    }
    sevenZOutputFile.closeArchiveEntry();

    CloseableUtils.close(in);
  }

  public static void compress(File input, File output) {
    if (input == null) {
      throw new RuntimeException("待压缩文件不存在");
    }

    try {
      if (output == null) {
        output = obtainDefaultOutput(input);
      }

      if (input.isFile()) {
        SevenZOutputFile sevenZOutput = new SevenZOutputFile(output);

        addEntry(input, input.getName(), sevenZOutput);

        CloseableUtils.close(sevenZOutput);
      } else {
        File[] files = input.listFiles();
        boolean flag = false;
        if (!ArrayUtils.isEmpty(files)) {
          SevenZOutputFile sevenZOutput = new SevenZOutputFile(output);
          for (File file : files) {
            if (file.isFile()) {
              flag = true;
              addEntry(file, file.getName(), sevenZOutput);
            } else {
              if (!ArrayUtils.isEmpty(file.listFiles())) {
                boolean result = addDir(file.listFiles(), file.getName(), sevenZOutput);

                if (result) {
                  flag = true;
                }
              }
            }
          }
        }

        if (!flag) {
          throw new RuntimeException("空目录，不存在待压缩文件");
        }
      }
    } catch (Exception e) {
      logger.error("", e);
    }
  }

  private static boolean addDir(File[] files, String prefix, SevenZOutputFile sevenZOutput)
      throws IOException {
    boolean flag = false;
    for (File file : files) {
      if (file.isFile()) {
        flag = true;
        addEntry(file, prefix + file.getName(), sevenZOutput);
      } else {
        if (!ArrayUtils.isEmpty(file.listFiles())) {
          boolean result = addDir(file.listFiles(), prefix + File.separator + file.getName(),
              sevenZOutput);
          if (result) {
            flag = true;
          }
        }
      }
    }
    return flag;
  }

  private static File obtainDefaultOutput(File input) throws IOException {
    return new File(input.getCanonicalPath() + File.separator + ".7z");
  }

  public static void z7z(String input, String output, String name) throws Exception {
    try {
      SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(output));
      SevenZArchiveEntry entry = null;

      File[] files = new File(input).listFiles();
      for (int i = 0; i < files.length; i++) {
        BufferedInputStream instream = null;
        instream = new BufferedInputStream(new FileInputStream(files[i]));
        if (name != null) {
          entry = sevenZOutput.createArchiveEntry(files[i], "b/" + name);
        } else {
          entry = sevenZOutput.createArchiveEntry(files[i], "b/" + files[i].getName());
        }
        sevenZOutput.putArchiveEntry(entry);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = instream.read(buffer)) > 0) {
          sevenZOutput.write(buffer, 0, len);
        }
        instream.close();
        sevenZOutput.closeArchiveEntry();
      }
      sevenZOutput.close();
    } catch (IOException ioe) {
      System.out.println(ioe.toString() + "  " + input);
    }
  }

  public static void main(String[] args) throws Exception {
//    z7z("/home/dslztx/Trash/test/a", "/home/dslztx/Trash/test/b.7z", null);
    File file = new File("../assist/target");
    System.out.println(file.getAbsolutePath());
    System.out.println(file.getCanonicalPath());
  }
}