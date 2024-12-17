package me.dslztx.assist.util.domain;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLParseBean {

    private String host;

    private String urlPath;

    public URLParseBean(String host, String urlPath) {
        this.host = host;
        this.urlPath = urlPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        URLParseBean URLParseBean = (URLParseBean)o;
        return Objects.equals(host, URLParseBean.host) && Objects.equals(urlPath, URLParseBean.urlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, urlPath);
    }
}
