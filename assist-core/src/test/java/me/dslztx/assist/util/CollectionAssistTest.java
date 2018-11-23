package me.dslztx.assist.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import me.dslztx.assist.pattern.strategy.ExcludeFilter;
import me.dslztx.assist.pattern.strategy.IncludeFilter;

public class CollectionAssistTest {

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

}