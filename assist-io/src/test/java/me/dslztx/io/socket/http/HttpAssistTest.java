package me.dslztx.io.socket.http;

import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.io.domain.HttpResult;

@Slf4j
public class HttpAssistTest {

    @Test
    public void httpGet() {
        try {
            Assert.assertTrue(HttpAssist.httpGet("http://www.baidu.com").getStatusLine().toString().contains("200"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    public void httpsGet() {
        try {
            Assert.assertTrue(HttpAssist.httpsGet("https://www.baidu.com").getStatusLine().toString().contains("200"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void httpPost() {
        try {
            HttpResult httpResult = HttpAssist.httpPost("http://127.0.0.1:30000/addUser", null,
                ContentType.APPLICATION_JSON, "{\"age\":100,\"username\":\"lmh\"}".getBytes());

            Assert.assertTrue(httpResult.toString().contains("lmh"));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void httpsPost() {
        try {
            HttpResult httpResult = HttpAssist.httpsPost("https://127.0.0.1:443/addUser", null,
                ContentType.APPLICATION_JSON, "{\"age\":100,\"username\":\"lmh\"}".getBytes());

            Assert.assertTrue(httpResult.toString().contains("lmh"));

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void httpsPost2() {
        try {
            HttpResult httpResult = HttpAssist.httpsPost("https://127.0.0.1:443/upload/datastream", null,
                ContentType.APPLICATION_OCTET_STREAM, "{\"age\":100,\"username\":\"lmh\"}".getBytes());

            Assert.assertTrue(httpResult.toString().contains("transfer data stream successfully"));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}