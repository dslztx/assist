package me.dslztx.assist.algorithm;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircleQueueTest {

    private static final Logger logger = LoggerFactory.getLogger(CircleQueueTest.class);

    @Test
    public void test() {
        try {
            CircleQueue<Long> queue = new CircleQueue<Long>(3);

            queue.push(1L);
            queue.push(2L);

            Assert.assertTrue(queue.top().equals(1L));

            queue.pop();
            queue.pop();

            queue.push(3L);
            queue.push(4L);
            queue.push(5L);

            Assert.assertTrue(queue.top().equals(3L));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}