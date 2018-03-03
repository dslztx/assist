package me.dslztx.assist.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class SevenZCompressUtils {

  private static final Logger logger = LoggerFactory.getLogger(SevenZCompressUtils.class);

  /**
   * 空目录不会被压缩
   */
  public static void compress(File input, File output) {
    SevenZOutputFile sevenZOutput = null;
    try {
      if (input == null) {
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
        if (!compressSubDirElements(input.listFiles(), input.getName(), sevenZOutput)) {
          output.delete();
          throw new RuntimeException("待压缩文件不存在");
        }
      }
    } catch (Exception e) {
      logger.error("", e);
    } finally {
      CloseableUtils.close(sevenZOutput);
    }
  }

  private static void addEntry(File file, String entryName, SevenZOutputFile sevenZOutputFile)
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

  private static boolean compressSubDirElements(File[] files, String prefix,
      SevenZOutputFile sevenZOutputFile)
      throws IOException {
    if (ArrayUtils.isEmpty(files)) {
      return false;
    }

    boolean flag = false;
    for (File file : files) {
      if (file.isFile()) {
        flag = true;
        addEntry(file, prefix + File.separator + file.getName(), sevenZOutputFile);
      } else {
        if (compressSubDirElements(file.listFiles(), prefix + File.separator + file.getName(),
            sevenZOutputFile)) {
          flag = true;
        }
      }
    }
    return flag;
  }

  private static File obtainDefaultOutput(File input) throws IOException {
    return new File(input.getCanonicalPath() + ".7z");
  }
}