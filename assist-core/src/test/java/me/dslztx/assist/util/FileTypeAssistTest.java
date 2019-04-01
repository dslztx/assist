package me.dslztx.assist.util;

import static org.junit.Assert.fail;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.bean.filetype.FileType;

public class FileTypeAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(FileTypeAssistTest.class);

    @Test
    public void recognizeImage() {
        try {
            Assert.assertTrue(FileTypeAssist.recognizeImage("1.png").equals(FileType.PNG));
            Assert.assertTrue(FileTypeAssist.recognizeImage("1.tif").equals(FileType.TIFF));
            Assert.assertTrue(FileTypeAssist.recognizeImage("1.ico").equals(FileType.ICO));

            Assert.assertTrue(FileTypeAssist.recognizeImage("1").equals(FileType.NOT_IMAGE));
            Assert.assertTrue(FileTypeAssist.recognizeImage("1.not").equals(FileType.NOT_IMAGE));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void recognizeImage1() {
        try {
            Assert.assertTrue(FileTypeAssist
                .recognizeImage(IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.png")))
                .equals(FileType.PNG));
            Assert.assertTrue(FileTypeAssist
                .recognizeImage(IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.tiff")))
                .equals(FileType.TIFF));

            Assert.assertTrue(FileTypeAssist.recognizeImage(new byte[0]).equals(FileType.NOT_IMAGE));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}