package me.dslztx.io;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteArrayInputStreamAugTest {
    private static final Logger logger = LoggerFactory.getLogger(ByteArrayInputStreamAugTest.class);

    String content =
        "X-CTCH-Pver: 0000001\r\nX-CTCH-A: AValue\r\nX-CTCH-B: BValue\r\nX-CTCH-C: CValue\r\nX-CTCH-D: DValue\r\nX-CTCH-TRUNCATE: false\r\nX-CTCH-SCORES: 5.46\r\nX-CTCH-RULES: DOMAIN_QUARTER_RCPT_CNT_1K_XX;...\r\nX-CTCH-UCDATE: 20160905\r\nX-CTCH-Size: 1096\r\nX-CTCH-DeferCnt: 0\r\n\r\nhello world this is a test!";

    @Test
    public void test() {
        try {
            byte[] bb = content.getBytes();

            ByteArrayInputStreamAug reader = new ByteArrayInputStreamAug(bb);

            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-Pver: 0000001"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-A: AValue"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-B: BValue"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-C: CValue"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-D: DValue"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-TRUNCATE: false"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-SCORES: 5.46"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-RULES: DOMAIN_QUARTER_RCPT_CNT_1K_XX;..."));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-UCDATE: 20160905"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-Size: 1096"));
            Assert.assertTrue(reader.readLineToCRLF().equals("X-CTCH-DeferCnt: 0"));
            Assert.assertTrue(reader.readLineToCRLF().equals(""));

            ByteArrayInputStream remainer = reader.createUsingRemain();

            byte[] cc = new byte[100];
            int len = remainer.read(cc, 0, cc.length);
            System.out.println(len);
            Assert.assertTrue(new String(cc, 0, len).equals("hello world this is a test!"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }

}