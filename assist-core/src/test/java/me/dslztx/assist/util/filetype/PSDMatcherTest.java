package me.dslztx.assist.util.filetype;

import static org.junit.Assert.fail;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ClassPathResourceAssist;

/**
 * PSD文件过大，为了节省空间，使用的测试PSD文件是非正常截断的
 */
public class PSDMatcherTest {

    private static final Logger logger = LoggerFactory.getLogger(PSDMatcherTest.class);

    @Test
    public void match() {
        try {
            PSDMatcher psdMatcher = new PSDMatcher();
            byte[] data = IOUtils.toByteArray(ClassPathResourceAssist.locateInputStream("images/1.psd"));
            Assert.assertTrue(psdMatcher.match(data));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}