package me.dslztx.assist.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dslztx
 */
public class IOUtils {

  private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

  public static BufferedReader bufferedReader(File file, Charset charset)
      throws FileNotFoundException {
    return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
  }

  public static BufferedReader bufferedReader(InputStream in, Charset charset) {
    return new BufferedReader(new InputStreamReader(in, charset));
  }

  public static BufferedReader bufferedReader(File file) throws FileNotFoundException {
    return bufferedReader(file, Charset.forName("UTF-8"));
  }

  public static BufferedReader bufferedReader(InputStream in) {
    return bufferedReader(in, Charset.forName("UTF-8"));
  }

  public static BufferedWriter bufferedWriter(File file, Charset charset)
      throws FileNotFoundException {
    return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
  }

  public static BufferedWriter bufferedWriter(OutputStream out, Charset charset) {
    return new BufferedWriter(new OutputStreamWriter(out, charset));
  }

  public static BufferedWriter bufferedWriter(File file) throws FileNotFoundException {
    return bufferedWriter(file, Charset.forName("UTF-8"));
  }

  public static BufferedWriter bufferedWriter(OutputStream out) {
    return bufferedWriter(out, Charset.forName("UTF-8"));
  }

  public static void closeResource(Closeable resource) {
    if (resource != null) {
      try {
        resource.close();
      } catch (Throwable e) {
        logger.error("", e);
      }
    }
  }
}
