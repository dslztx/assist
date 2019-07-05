package me.dslztx.assist.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.pattern.strategy.ExcludeFilter;
import me.dslztx.assist.pattern.strategy.IncludeFilter;

public class CollectionAssistTest {

    private static final Logger logger = LoggerFactory.getLogger(CollectionAssistTest.class);

    @Test
    public void isEmptyTest() throws Exception {
        try {
            assertTrue(CollectionAssist.isEmpty(null));
            assertTrue(CollectionAssist.isEmpty(new ArrayList<String>()));
            List<String> strList = new ArrayList<String>();
            strList.add("hello");
            assertFalse(CollectionAssist.isEmpty(strList));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertToIntArray() {
        try {
            List<Integer> a = new ArrayList<Integer>();
            a.add(1);
            a.add(2);
            a.add(3);

            Assert.assertTrue(Arrays.equals(CollectionAssist.convertToIntArray(a), new int[] {1, 2, 3}));

            List<Integer> b = new ArrayList<Integer>();
            Assert.assertTrue(CollectionAssist.convertToIntArray(b).length == 0);

            List<Integer> c = new ArrayList<Integer>();
            c.add(1);
            c.add(2);
            c.add(null);
            c.add(3);

            Assert.assertTrue(Arrays.equals(CollectionAssist.convertToIntArray(c), new int[] {1, 2, 3}));

            List<Integer> d = new ArrayList<Integer>();
            d.add(null);
            d.add(null);
            d.add(null);

            Assert.assertTrue(CollectionAssist.convertToIntArray(d).length == 0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void filterIncludeTest() {
        try {
            List<String> list = new ArrayList<String>();
            list.add("hello");
            list.add("world");
            list.add("null");
            list.add(null);

            List<String> to = new ArrayList<String>();

            CollectionAssist.filterInclude(list, new IncludeFilter<String>() {
                @Override
                public boolean include(String s) {
                    if (s != null && s.startsWith("w")) {
                        return true;
                    }
                    return false;
                }
            }, to);

            Assert.assertTrue(to.get(0).equals("world"));
            Assert.assertTrue(to.size() == 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void excludeExcludeTest() {
        try {
            List<String> list = new ArrayList<String>();
            list.add("hello");
            list.add("world");
            list.add("null");
            list.add(null);

            List<String> to = new ArrayList<String>();

            CollectionAssist.filterExclude(list, new ExcludeFilter<String>() {
                @Override
                public boolean exclude(String s) {
                    if (s != null && s.startsWith("w")) {
                        return true;
                    }
                    return false;
                }
            }, to);

            Assert.assertTrue(to.get(0).equals("hello"));
            Assert.assertTrue(to.get(1).equals("null"));
            Assert.assertTrue(to.get(2) == null);
            Assert.assertTrue(to.size() == 3);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void toArrayTest() {
        try {
            ArrayList<String> mm = new ArrayList<String>();
            mm.add("hello");
            mm.add("world");

            Assert.assertTrue(CollectionAssist.toArray(mm, String.class).length == 2);
            Assert.assertTrue(CollectionAssist.toArray(mm, String.class).getClass().getComponentType() == String.class);
            Assert
                .assertTrue(Arrays.equals(CollectionAssist.toArray(mm, String.class), new String[] {"hello", "world"}));

            ArrayList<String> nn = new ArrayList<String>();
            Assert.assertTrue(CollectionAssist.toArray(nn, String.class).length == 0);

            ArrayList<String> pp = null;
            Assert.assertNull(CollectionAssist.toArray(pp, String.class));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

}