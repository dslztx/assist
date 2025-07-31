package me.dslztx.assist.client.redis;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceTest.class);

    @BeforeAll
    public static void init0() {
        try {
            Map<String, String> kvs = new HashMap<>();
            kvs.put("key1#luomhtest", "value1@luomhtest");
            kvs.put("key2#luomhtest", "value2@luomhtest");
            kvs.put("key3#luomhtest", "value3@luomhtest");

            RedisFutureProxy<String> result = RedisService.msetAsync("in", kvs);

            assertTrue(result.get(10, TimeUnit.SECONDS).equals("OK"));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @AfterAll
    public static void destroy0() {
        try {
            RedisFutureProxy<Long> result2 =
                RedisService.mdelAsync("in", "key1#luomhtest", "key2#luomhtest", "key3" + "#luomhtest");

            assertTrue(result2.get(10, TimeUnit.SECONDS).compareTo(0L) > 0);
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    @Order(1)
    public void test0() {
        try {
            long t = System.currentTimeMillis() / 1000 + 10;
            RedisService.expireAtAsync("in", "key1#luomhtest", t);

            Thread.sleep(15000L);

            RedisFutureProxy<Long> result2 =
                RedisService.existAsync("in", "key1#luomhtest", "key2#luomhtest", "key3" + "#luomhtest");
            assertTrue(result2.get(20, TimeUnit.SECONDS).equals(2L));

        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    @Order(2)
    public void loadLuaScriptTest() {
        try {

            RedisFutureProxy<String> proxy = RedisService.luaScriptLoadAsync("in",
                "local current = redis.call('INCR', KEYS[1]); redis.call('EXPIRE', KEYS[1], 86400); return current;");

            String luaScriptSHA = proxy.get(30, TimeUnit.SECONDS);

            Assertions.assertTrue("6527aefefb661192b731a580865cd01d5731a4ca".equals(luaScriptSHA));

        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    @Order(3)
    public void luaScriptSHAExistTest() {
        try {

            RedisFutureProxy<List<Boolean>> proxy =
                RedisService.luaScriptSHAExistAsync("in", "6527aefefb661192b731a580865cd01d5731a4ca", "notexistsha");

            List<Boolean> valueList = proxy.get(50, TimeUnit.SECONDS);

            Assertions.assertTrue(Boolean.TRUE.equals(valueList.get(0)));
            Assertions.assertTrue(Boolean.FALSE.equals(valueList.get(1)));

        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

    @Test
    @Order(4)
    public void evalLuaScriptSHASingleKeyLongResultTest() {
        try {

            RedisFutureProxy<Long> proxy = RedisService.evalLuaScriptSHASingleKeyLongResultAsync("in",
                "6527aefefb661192b731a580865cd01d5731a4ca", "lmhtest");

            Long value = proxy.get(50, TimeUnit.SECONDS);

            Assertions.assertTrue(value > 0);

        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }

}