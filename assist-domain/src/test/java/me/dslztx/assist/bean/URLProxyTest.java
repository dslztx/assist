package me.dslztx.assist.bean;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class URLProxyTest {

    @Test
    public void test0() {
        try {
            URLProxy urlProxy = new URLProxy(
                    "http://testuser:testpass@www.aspxfans.com:8080/news/index.asp?boardID=5&ID=24618&page=1#refpart");


            Assert.assertTrue("[Protocol] http\n[Userinfo] testuser:testpass\n[Host] www.aspxfans.com\n[Port] 8080\n[Path] /news/index.asp\n[Query] boardID=5&ID=24618&page=1\n[Ref] refpart".equalsIgnoreCase(urlProxy.printURLInfo()));

            Assert.assertTrue("[Protocol] http\n[Userinfo] testuser:testpass\n[Host] www.aspxfans.com\n[Port] 8080\n[Path] /news/index.asp\n[Query] boardID=5&ID=24618&page=1\n[Ref] refpart\n[Authority] testuser:testpass@www.aspxfans.com:8080\n[File] /news/index.asp?boardID=5&ID=24618&page=1\n[DefaultPort] 80".equalsIgnoreCase(urlProxy.printURLInfoFull()));
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

}