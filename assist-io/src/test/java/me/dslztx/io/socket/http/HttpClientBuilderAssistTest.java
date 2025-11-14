package me.dslztx.io.socket.http;

import java.nio.charset.StandardCharsets;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientBuilderAssistTest {

    @Test
    @Ignore
    public void test0() {
        try {

            CloseableHttpClient httpClient =
                HttpClientBuilderAssist.buildConnectionReuseHttpClient(10, 2, 60000, 60000, 60000);

            HttpRequestAssist.httpGet(httpClient, "http://127.0.0.1:20040/hello/way", StandardCharsets.UTF_8);

            Thread.sleep(30000L);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void test1() {
        try {
            CloseableHttpClient httpClient =
                HttpClientBuilderAssist.buildNoConnectionReuseHttpClient(60000, 60000, 60000);

            HttpRequestAssist.httpGet(httpClient, "http://127.0.0.1:20040/hello/way", StandardCharsets.UTF_8);

            Thread.sleep(30000L);
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }
}