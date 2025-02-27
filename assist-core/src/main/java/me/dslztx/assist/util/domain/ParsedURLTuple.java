package me.dslztx.assist.util.domain;

public class ParsedURLTuple {

    String url;

    String protocol;

    /**
     * top-level-domain，即顶级域名：须包含"."，比如".com",".cn",".中国"
     */
    String tld;

    int start;

    int end;

    public ParsedURLTuple(String url, String protocol, String tld, int start, int end) {
        this.url = url;
        this.protocol = protocol;
        this.tld = tld;
        this.start = start;
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "ParsedURLTuple{" + "url='" + url + '\'' + ", protocol='" + protocol + '\'' + ", tld='" + tld + '\''
            + ", start=" + start + ", end=" + end + '}';
    }
}
