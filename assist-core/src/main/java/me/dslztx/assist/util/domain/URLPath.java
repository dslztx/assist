package me.dslztx.assist.util.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLPath {

    private String urlPath;

    private String query;

    private String anchor;


    public URLPath(String urlPath, String query, String anchor) {
        this.urlPath = urlPath;
        this.query = query;
        this.anchor = anchor;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }
}
