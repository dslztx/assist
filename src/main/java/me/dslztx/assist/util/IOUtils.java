package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import me.dslztx.assist.struct.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {

  private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

  public static BufferedReader bufferedReader(File file, Charset charset)
      throws FileNotFoundException {
    return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
  }

  public static BufferedReader bufferedReader(File file) throws FileNotFoundException {
    return bufferedReader(file, Charset.forName("UTF-8"));
  }

  public static BufferedWriter bufferedWriter(File file, Charset charset)
      throws FileNotFoundException {
    return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
  }

  public static BufferedWriter bufferedWriter(File file) throws FileNotFoundException {
    return bufferedWriter(file, Charset.forName("UTF-8"));
  }

  public static void closeResource(Closeable resource) {
    if (resource != null) {
      try {
        resource.close();
      } catch (Exception e) {
        logger.error("", e);
      }
    }
  }

  public static void loadFile(File file, Process process) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String line = null;
      while ((line = in.readLine()) != null) {
        process.parse(line);
      }
    } catch (Exception e) {
      logger.error("", e);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception e) {
        logger.error("", e);
      }
    }
  }

  public static Writer fetchWriter(File file) {
    try {
      return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    } catch (Exception e) {
      logger.error("", e);
      return null;
    }
  }

  public static void returnWriter(Writer writer) {
    try {
      if (writer != null) {
        writer.close();
      }
    } catch (Exception e) {
      logger.error("", e);
    }
  }
}
