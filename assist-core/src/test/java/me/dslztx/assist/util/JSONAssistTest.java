package me.dslztx.assist.util;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONAssistTest {

    @Test
    public void parseObjectForbidMapOrCollectionOrArrayTest() {
        try {
            ObjA obj0 = new ObjA("dslztx", 10);

            ObjA obj1 = JSONAssist.parseObjectForbidMapOrCollectionOrArray(JSONAssist.toJSONString(obj0), ObjA.class);

            Assert.assertTrue(obj0 != obj1);
            Assert.assertTrue(obj0.equals(obj1));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseMap() {
        try {
            Map<String, Integer> map0 = new HashMap<>();
            map0.put("1", 10);
            map0.put("2", 20);

            Map<String, Integer> map1 = JSONAssist.parseMap(JSONAssist.toJSONString(map0), String.class, Integer.class);
            Assert.assertTrue(map0 != map1);
            Assert.assertTrue(map0.equals(map1));

            Map<String, ObjA> map2 = new HashMap<>();
            map2.put("1", new ObjA("dslztx", 10));
            map2.put("2", new ObjA("dslztx", 20));

            Map<String, ObjA> map3 = JSONAssist.parseMap(JSONAssist.toJSONString(map2), String.class, ObjA.class);

            Assert.assertTrue(map2 != map3);
            Assert.assertTrue(map2.equals(map3));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseList() {
        try {
            List<String> list0 = new ArrayList<>();
            list0.add("one");
            list0.add("two");

            List<String> list1 = JSONAssist.parseList(JSONAssist.toJSONString(list0), String.class);
            Assert.assertTrue(list1 != list0);
            Assert.assertTrue(list1.equals(list0));

            List<ObjA> list2 = new ArrayList<>();
            list2.add(new ObjA("dslztx", 10));
            list2.add(new ObjA("dslztx", 20));

            List<ObjA> list3 = JSONAssist.parseList(JSONAssist.toJSONString(list2), ObjA.class);

            Assert.assertTrue(list3 != list2);
            Assert.assertTrue(list3.equals(list2));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void parseArray() {
        try {
            String[] ss0 = new String[] {"one", "two"};

            String[] ss1 = JSONAssist.parseArray(JSONAssist.toJSONString(ss0), String.class);

            Assert.assertTrue(ss0 != ss1);

            Assert.assertTrue(ObjectAssist.equalsGenerally(ss0, ss1));

            ObjA[] ss2 = new ObjA[2];
            ss2[0] = new ObjA("dslztx", 10);
            ss2[1] = new ObjA("dslztx", 20);

            ObjA[] ss3 = JSONAssist.parseArray(JSONAssist.toJSONString(ss2), ObjA.class);

            Assert.assertTrue(ss2 != ss3);
            Assert.assertTrue(ObjectAssist.equalsGenerally(ss2, ss3));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test0() {
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("one", 1);
            // fastjson报错
            map.put("two", null);

            String s = "{\"one\":1,\"two\":null}";

            Assert.assertTrue(JSONAssist.toJSONString(map).equalsIgnoreCase(s));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test1() {
        try {
            // fastjson报错，循环引用
            ObjA objA = new ObjA("dslztx", 10);

            Map<String, ObjA> map = new HashMap<>();
            map.put("1", objA);
            map.put("2", objA);
            map.put("3", objA);

            ObjB objB = new ObjB();
            objB.setMap(map);

            ObjB objB1 = JSONAssist.parseObjectForbidMapOrCollectionOrArray(JSONAssist.toJSONString(objB), ObjB.class);

            Assert.assertTrue(objB1 != objB);
            Assert.assertTrue(objB.equals(objB1));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test2() {
        try {
            JSONAssist.parseObjectForbidMapOrCollectionOrArray("", Map.class);
        } catch (UnsupportedOperationException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test3() {
        try {
            JSONAssist.parseObjectForbidMapOrCollectionOrArray("", List.class);
        } catch (UnsupportedOperationException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void test4() {
        try {
            JSONAssist.parseObjectForbidMapOrCollectionOrArray("", new String[2].getClass());
        } catch (UnsupportedOperationException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}

class ObjA {

    String name;

    int age;

    public ObjA() {}

    public ObjA(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ObjA objA = (ObjA)o;
        return age == objA.age && Objects.equals(name, objA.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

class ObjB {
    Map<String, ObjA> map = new HashMap<>();

    public ObjB() {}

    public Map<String, ObjA> getMap() {
        return map;
    }

    public void setMap(Map<String, ObjA> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ObjB objB = (ObjB)o;
        return Objects.equals(map, objB.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}