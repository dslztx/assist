package me.dslztx.io.domain;

import java.io.IOException;
import java.util.Objects;

import org.apache.http.Consts;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;
import me.dslztx.assist.util.StringAssist;

@Slf4j
public class HttpResult {

    StatusLine statusLine;

    String message;

    public static HttpResult generateFrom(CloseableHttpResponse response) {
        HttpResult httpResult = new HttpResult();

        if (Objects.isNull(response)) {
            return httpResult;
        }

        try {
            httpResult.setStatusLine(response.getStatusLine());
            httpResult.setMessage(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
        } catch (IOException e) {
            log.error("", e);
        }

        return httpResult;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return StringAssist.format("[StatusLine]{};;[Message]{}", statusLine, message);
    }
}
