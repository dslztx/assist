package me.dslztx.assist.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

public class ObjectAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(ObjectAssistTest.class);

    @Test
    public void equalsGenerallyTest() {
        try {
            TestObject a = new TestObject();

            TestObject b = new TestObject();
            List<Object> ll = new ArrayList<Object>();
            ll.add("a");
            ll.add("b");

            b.ee = ll;

            Assert.assertFalse(ObjectAssist.equals(a, b));
            Assert.assertFalse(ObjectAssist.equalsGenerally(a, b));

            TestObject c = new TestObject();

            Assert.assertFalse(ObjectAssist.equals(a, c));
            Assert.assertTrue(ObjectAssist.equalsGenerally(a, c));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}

class TestObject {

    int a = 100000;

    Integer b = 20000;

    String c = "hello world";

    char[] cc = new char[] {'a', 'b'};
    Character[] dd = new Character[] {'c', 'd'};

    List<Object> ee = new ArrayList<Object>();

    {
        ee.add("String");
        ee.add(b);
    }

}