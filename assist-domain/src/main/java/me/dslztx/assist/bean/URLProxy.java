package me.dslztx.assist.bean;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Objects;

@Slf4j
public class URLProxy {

    private URL innerURL;

    public URLProxy(String url) {
        this.innerURL = parse(url);
    }

    private URL parse(String url) {
        try {
            return new URL(url);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public String printURLInfo() {
        if (Objects.isNull(innerURL)) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[Protocol] ").append(innerURL.getProtocol()).append("\n");
            sb.append("[Userinfo] ").append(innerURL.getUserInfo()).append("\n");
            sb.append("[Host] ").append(innerURL.getHost()).append("\n");
            sb.append("[Port] ").append(innerURL.getPort()).append("\n");
            sb.append("[Path] ").append(innerURL.getPath()).append("\n");
            sb.append("[Query] ").append(innerURL.getQuery()).append("\n");
            sb.append("[Ref] ").append(innerURL.getRef());
            return sb.toString();
        }
    }

    public String printURLInfoFull() {
        if (Objects.isNull(innerURL)) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("[Protocol] ").append(innerURL.getProtocol()).append("\n");
            sb.append("[Userinfo] ").append(innerURL.getUserInfo()).append("\n");
            sb.append("[Host] ").append(innerURL.getHost()).append("\n");
            sb.append("[Port] ").append(innerURL.getPort()).append("\n");
            sb.append("[Path] ").append(innerURL.getPath()).append("\n");
            sb.append("[Query] ").append(innerURL.getQuery()).append("\n");
            sb.append("[Ref] ").append(innerURL.getRef()).append("\n");
            sb.append("[Authority] ").append(innerURL.getAuthority()).append("\n");
            sb.append("[File] ").append(innerURL.getFile()).append("\n");
            sb.append("[DefaultPort] ").append(innerURL.getDefaultPort());
            return sb.toString();
        }
    }
}
