package me.dslztx.assist.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class CompressZIPAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(CompressZIPAssistTest.class);

    private static final String PATH_PREFIX = "src/test/resources";

    @Test
    public void compressAndDecompress() throws Exception {
        try {
            File original = new File(PATH_PREFIX + File.separator + "a");
            File compressed = new File(PATH_PREFIX + File.separator + "test" + File.separator + "a.zip");
            compressed.getParentFile().mkdirs();

            CompressZIPAssist.compress(original, compressed);

            CompressZIPAssist.decompress(compressed, compressed.getParentFile());

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
            File compressed = new File(PATH_PREFIX + File.separator + "test" + File.separator + "1.xml.zip");
            compressed.getParentFile().mkdirs();

            CompressZIPAssist.compress(original, compressed);

            CompressZIPAssist.decompress(compressed, compressed.getParentFile());

            Assert.assertTrue(FileAssist.isContentSame(original,
                new File(compressed.getParentFile().getCanonicalPath() + File.separator + "1.xml")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        } finally {
            FileAssist.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
        }
    }

    @Test
    public void compressAndDecompress3() throws Exception {
        try {
            File original = new File(PATH_PREFIX + File.separator + "a");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            File dst = new File(PATH_PREFIX + File.separator + "test");
            dst.mkdirs();

            CompressZIPAssist.compress(original, byteArrayOutputStream);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            CompressZIPAssist.decompress(byteArrayInputStream, dst);

            Assert.assertTrue(FileAssist.isDirSame(original, new File(dst.getCanonicalPath() + File.separator + "a")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        } finally {
            FileAssist.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
        }
    }

    @Test
    public void compressAndDecompress4() throws Exception {
        try {
            File original = new File(PATH_PREFIX + File.separator + "1.xml");

            File dst = new File(PATH_PREFIX + File.separator + "test");
            dst.mkdirs();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            CompressZIPAssist.compress(original, byteArrayOutputStream);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            CompressZIPAssist.decompress(byteArrayInputStream, dst);

            Assert.assertTrue(
                FileAssist.isContentSame(original, new File(dst.getCanonicalPath() + File.separator + "1.xml")));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        } finally {
            FileAssist.delFileRecursiveForce(new File(PATH_PREFIX + File.separator + "test"));
        }
    }
}