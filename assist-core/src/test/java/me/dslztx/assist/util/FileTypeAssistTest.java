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

    @Test
    public void isGeneralFileTest() {
        try {
            Assert.assertTrue(FileTypeAssist.isGeneralFile("1.txt"));
            Assert.assertTrue(FileTypeAssist.isGeneralFile("1.mp3"));
            Assert.assertTrue(FileTypeAssist.isGeneralFile("1.mp4"));
            Assert.assertFalse(FileTypeAssist.isGeneralFile("1.wps"));
            Assert.assertFalse(FileTypeAssist.isGeneralFile("1"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    public void isMaskImageTest() {
        try {
            byte[] dd = new byte[10];
            dd[0] = -1;
            dd[1] = -40;
            dd[2] = -1;
            dd[3] = -32;
            dd[4] = 0;
            dd[5] = 16;
            dd[6] = 74;
            dd[7] = 70;
            dd[8] = -1;
            dd[9] = (byte)0xd9;

            Assert.assertTrue(FileTypeAssist.isMaskImage("1.xls", dd));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}