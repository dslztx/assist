package me.dslztx.assist.algorithm;

import java.util.Arrays;

import me.dslztx.assist.util.ObjectAssist;

public class PatternHit {
    int start;

    int end;

    String value;

    public PatternHit(int start, int end, String value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PatternHit that = (PatternHit)o;
        return start == that.start && end == that.end && ObjectAssist.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {start, end, value});
    }
}
