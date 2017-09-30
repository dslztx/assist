package me.dslztx.assist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author dslztx
 * @date 2015年11月12日
 * @version 2015.11.12
 * @keynote The load(Reader) / store(Writer, String) methods load and store properties from and to a character based
 *          stream in a simple line-oriented format specified below. The load(InputStream) / store(OutputStream, String)
 *          methods work the same way as the load(Reader)/store(Writer, String) pair, except the input/output stream is
 *          encoded in ISO 8859-1 character encoding. The loadFromXML(InputStream) and storeToXML(OutputStream, String,
 *          String) methods load and store properties in a simple XML format. By default the UTF-8 character encoding is
 *          used, however a specific encoding may be specified if required. This class is thread-safe: multiple threads
 *          can share a single Properties object without the need for external synchronization.
 */
public class PropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    /**
     * load properties object from file,given the appropriate charset
     * 
     * @param file
     * @param charset
     * @return
     * @throws IOException
     */
    public static Properties loadFromFile(File file, Charset charset) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            throw e;
        } finally {
            // the best method to return resource
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    /**
     * store properties object to file,given the appropriate charset
     * 
     * @param properties
     * @param file
     * @param charset
     * @throws IOException
     */
    public static void storeToFile(Properties properties, File file, Charset charset) throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            properties.store(writer, "");
        } catch (IOException e) {
            throw e;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    /**
     * load properties object from XML file,need not specify the charset,the charset is read from 'encoding' attribute
     * in xml file
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Properties loadFromXMLFile(File file) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            Properties properties = new Properties();
            properties.loadFromXML(in);
            return properties;
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    /**
     * store properties object to xml file,given the appropriate charset.And the 'encoding' attribute in xml file is set
     * to given charset
     * 
     * @param properties
     * @param file
     * @param charset
     * @throws IOException
     */
    public static void storeToXMLFile(Properties properties, File file, Charset charset) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            properties.storeToXML(out, "", charset.name());
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }
}
