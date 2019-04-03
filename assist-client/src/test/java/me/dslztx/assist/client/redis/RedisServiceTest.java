package me.dslztx.assist.client.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class RedisServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceTest.class);

    @Test
    public void obtainRedisClient() {
        try {
            RedisFutureProxy<String> z1 = RedisService.setAsync("in", "a1", "v1", 60L);
            RedisFutureProxy<String> z2 = RedisService.setAsync("in", "a2", "v2", 60L);

            String vv1 = z1.get(1, TimeUnit.SECONDS);
            String vv2 = z2.get(1, TimeUnit.SECONDS);
            Assert.assertTrue("OK".equals(vv1));
            Assert.assertTrue("OK".equals(vv2));

            RedisFutureProxy<List<String>> result = RedisService.mgetAsync("in", "a1", "a2");

            List<String> values = result.get(1, TimeUnit.SECONDS);

            Assert.assertTrue(values.get(0).equals("v1"));
            Assert.assertTrue(values.get(1).equals("v2"));

            System.out.println(z1.obtainServer());
            System.out.println(z2.obtainServer());
            System.out.println(result.obtainServer());

            Map<String, String> map = new HashMap<String, String>();
            map.put("mapKey1", "mapValue1");
            map.put("mapKey2", "mapValue2");
            map.put("mapKey3", "mapValue3");

            RedisFutureProxy<String> result2 = RedisService.msetAsync("in", map);
            Assert.assertTrue(result2.get(100, TimeUnit.MILLISECONDS).equals("OK"));
        } catch (Exception e) {
            logger.error("", e);
            Assert.fail();
        }
    }
}