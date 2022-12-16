package me.dslztx.assist.solution;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaObjectSizeCalcTest {

    @Test
    public void test0() {
        try {
            byte[] bb = new byte[3 * 1024 * 1024];

            // 还有对象头所占据的字节，所以是大于
            Assert.assertTrue(JavaObjectSizeCalc.cal(bb) > 3145728);

            Assert.assertTrue(JavaObjectSizeCalc.cal(null) == 0);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

}