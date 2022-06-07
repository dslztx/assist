package me.dslztx.assist.util;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCompressAssistTest {

    @Test
    public void compressUsingGZIP() {
        try {

            String s =
                "《庄子·齐物论》中说，有一天，庄周在草地上睡觉，做了一个梦。他在睡梦中觉得自己变成了一只蝴蝶，蝴蝶在空中翩翩然飞舞着，四处游荡，快乐得忘记了自己本来的样子，也忘了自己是由庄周变化而成的。过了一会儿，庄周忽然醒了过来，但是梦境还清晰地印在他的脑海里。他起身看了看自己，又想了想梦中的事情，一时间有些迷惘。他竟然弄不清自己到底是庄周还是蝴蝶了。究竟是他在自己的梦中变成了蝴蝶，还是蝴蝶在它的梦中变成了庄周？竟然分不清哪一个是真的。这件事让庄周很有感触，他觉得，有时人生中的梦境和真实的生活是很难区分开的。梦境有时会给人一种真实的感受，而在真实的生活中也会让人有身在梦中的感觉。庄周认为，世间万物就是这样不断变化着的，人生也是这样不停变幻着的，没有什么是永恒不变的。";

            String compressedStr = StringCompressAssist.compressUsingGZIP(s);

            Assert.assertTrue(StringCompressAssist.uncompressUsingGZIP(compressedStr).equals(s));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

}