package me.dslztx.assist.util;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class Compress7ZAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(Compress7ZAssistTest.class);

    private static final String PATH_PREFIX = "src/test/resources";

    @Test
    public void compressAndDecompress() throws Exception {
        try {
            File original = new File(PATH_PREFIX + File.separator + "a");
            File compressed = new File(PATH_PREFIX + File.separator + "test" + File.separator + "a.7z");
            compressed.getParentFile().mkdirs();

            Compress7ZAssist.compress(original, compressed);

            Compress7ZAssist.decompress(compressed, compressed.getParentFile());

            Assert.assertTrue(FileAssist.isDirSame(original,
                new File(compressed.getParentFile().getCanonicalPath() + File.separator + "a")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        } finally {
            FileAssist.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
        }
    }

    @Test
    public void compressAndDecompress2() throws Exception {
        try {
            File original = new File(PATH_PREFIX + File.separator + "1.xml");
            File compressed = new File(PATH_PREFIX + File.separator + "test" + File.separator + "1.xml.7z");
            compressed.getParentFile().mkdirs();

            Compress7ZAssist.compress(original, compressed);

            Compress7ZAssist.decompress(compressed, compressed.getParentFile());

            Assert.assertTrue(FileAssist.isContentSame(original,
                new File(compressed.getParentFile().getCanonicalPath() + File.separator + "1.xml")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        } finally {
            FileAssist.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
        }
    }
}