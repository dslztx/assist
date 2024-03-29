package me.dslztx.io.socket.http;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class HttpClientAssistTest {

    @Test
    @Disabled
    void testHttpPostJSONUTF8() {
        try {

            MailContent mailContent = new MailContent();
            mailContent.setSubject("test subject");
            mailContent.setAccounts("dslztx@gmail.com");
            mailContent.setContent("test content");

            ObjectMapper objectMapper = new ObjectMapper();
            HttpClientAssist.httpPostJSONUTF8("http://10.110.20.142:18181/alert/sendMail",
                objectMapper.writeValueAsString(mailContent));

        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Test
    @Disabled
    void testHttpPostJSONUTF8WithHeader() {
        try {
            MailContent mailContent = new MailContent();
            mailContent.setSubject("test subject");
            mailContent.setAccounts("dslztx@gmail.com");
            mailContent.setContent("test content");

            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("Host", "alert-center.test.qzgw.internal");

            ObjectMapper objectMapper = new ObjectMapper();
            HttpClientAssist.httpPostJSONUTF8WithHeader("http://10.110.20.142:18181/alert/sendMail", headerMap,
                objectMapper.writeValueAsString(mailContent));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Test
    void testHttpGetUTF8() {
        try {
            Assertions.assertTrue(HttpClientAssist.httpGetUTF8("http://www.baidu.com").contains("STATUS OK"));
        } catch (Exception e) {
            log.error("", e);
        }
    }
}

class MailContent {
    String accounts;

    String subject;

    String content;

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}