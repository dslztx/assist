package me.dslztx.io.socket.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;

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

            HttpClientAssist.httpPostJSONUTF8("http://10.110.20.142:18181/alert/sendMail",
                JSON.toJSONString(mailContent));
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