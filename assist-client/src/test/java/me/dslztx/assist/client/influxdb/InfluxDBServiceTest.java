package me.dslztx.assist.client.influxdb;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.DateTimeAssist;

@Ignore
public class InfluxDBServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBServiceTest.class);

    @Test
    public void insertPoint() {
        try {
            InfluxDBPoint point = new InfluxDBPoint();

            point.setTimestamp(DateTimeAssist.generate(2019, 4, 10, 10, 20, 10).getTime());

            point.setMeasurement("measurementTest2");

            point.addField("field1", 20.005);

            point.addTag("tag1", "hello");

            String urlString = "http://127.0.0.1:9086/write?db=test&precision=ms";

            Assert.assertTrue(InfluxDBService.insertPoint(urlString, point));
        } catch (Exception e) {
            logger.error("", e);
            fail();
        }
    }
}