package me.dslztx.assist.algorithm;

public class Hit {
    int start;

    int end;

    String value;

    public Hit(int start, int end, String value) {
        this.start = start;
        this.end = end;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("[%d:%d]=%s", this.start, this.end, this.value);
    }
}
