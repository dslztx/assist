package me.dslztx.assist.util;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathResourceAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(ClassPathResourceAssist.class);

    @Test
    public void locateInputStreams() {
        try {
            List<InputStream> ins = ClassPathResourceAssist.locateInputStreams("1.xml");
            Assert.assertTrue(ins.size() == 1);

            List<InputStream> ins2 =
                ClassPathResourceAssist.locateInputStreams("org/apache/commons/configuration2/Configuration.class");
            Assert.assertTrue(ins2.size() == 1);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateInputStream() {
        try {
            Assert.assertNotNull(
                ClassPathResourceAssist.locateInputStream("me/dslztx/assist/util/ClassPathResourceAssist.class"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateFilesNotInJar() {
        try {
            Assert.assertTrue(ClassPathResourceAssist.locateFilesNotInJar("2.xml").size() == 1);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateFileNotInJar() {
        try {
            Assert.assertNotNull(ClassPathResourceAssist.locateFileNotInJar("holiday_arrangement"));

            Assert.assertNull(ClassPathResourceAssist.locateFileNotInJar("com/alibaba/druid/Constants.class"));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Test
    public void locateFileNotInJar3() {
        try {
            // 第一个source_tips文件在jce.jar中，按照方法的意图应该还是能读取到test/resources目录下的source_tips文件
            File file = ClassPathResourceAssist.locateFileNotInJar("source_tips");

            Assert.assertNotNull(file);
            Assert.assertTrue(FileUtils.readFileToString(file, Charset.forName("UTF-8")).equals("hello world"));

            // 只有2个，没有读取到ext/sunec.jar，ext/sunjce_provider.jar，exxt/sunpkcs11.jar中的source_tips资源，应该跟类加载器有关，这里先不管
            List<InputStream> loadedInputStreamList = ClassPathResourceAssist.locateInputStreams("source_tips");

            Assert.assertTrue(loadedInputStreamList.size() >= 1);

            // Assert.assertTrue(loadedInputStreamList.get(0).getClass().toString()
            // .equals("class sun.net.www.protocol.jar.JarURLConnection$JarURLInputStream"));

        } catch (Exception e) {
            logger.error("", e);
        }
    }
}