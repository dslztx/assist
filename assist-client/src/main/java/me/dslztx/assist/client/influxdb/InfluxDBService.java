package me.dslztx.assist.client.influxdb;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.StringAssist;

public class InfluxDBService {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBService.class);

    /**
     *
     * @param urlString 比如 "http://127.0.0.1:8086/write?db=test&precision=ms"
     * @param point
     */
    public static boolean insertPoint(String urlString, InfluxDBPoint point) {
        if (StringAssist.isBlank(urlString)) {
            throw new RuntimeException("urlString is empty");
        }

        if (!point.isValid()) {
            throw new RuntimeException("influxdb point is invalid");
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String bodyString = point.lineProtocolString();
            OutputStream os = conn.getOutputStream();
            os.write(bodyString.getBytes("utf-8"));
            os.flush();
            os.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK
                || conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                return true;
            } else {
                logger.info("influxdb insert fail , the return error code is {}", conn.getResponseCode());
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return false;
    }
}
