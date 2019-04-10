package me.dslztx.assist.client.influxdb;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.CollectionAssist;
import me.dslztx.assist.util.ObjectAssist;
import me.dslztx.assist.util.StringAssist;

public class InfluxDBPoint {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBPoint.class);

    private long timestamp = -1;

    private String measurement;

    private List<FieldKeyValue> fields = new ArrayList<FieldKeyValue>();

    private List<TagKeyValue> tags = new ArrayList<TagKeyValue>();

    public String lineProtocolString() {
        StringBuilder sb = new StringBuilder();

        sb.append(measurement);

        if (CollectionAssist.isNotEmpty(tags)) {
            for (TagKeyValue kv : tags) {
                sb.append(",");
                sb.append(kv.toString());
            }
        }

        sb.append(" ");

        if (CollectionAssist.isNotEmpty(fields)) {
            for (FieldKeyValue kv : fields) {
                sb.append(kv.toString());
                sb.append(",");
            }
        }

        if (sb.length() >= 1) {
            sb.setLength(sb.length() - 1);
        }

        sb.append(" ");

        sb.append(timestamp);

        return sb.toString();
    }

    public void addTag(String key, String value) {
        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (StringAssist.isBlank(value)) {
            throw new RuntimeException("value is blank");
        }

        tags.add(new TagKeyValue(key, value));
    }

    public void addField(String key, Number value) {
        if (StringAssist.isBlank(key)) {
            throw new RuntimeException("key is blank");
        }

        if (ObjectAssist.isNull(value)) {
            throw new RuntimeException("value is null");
        }

        fields.add(new FieldKeyValue(key, value));
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public boolean isValid() {
        if (timestamp == -1) {
            logger.info("timestamp is not set");
            return false;
        }

        if (StringAssist.isBlank(measurement)) {
            logger.info("measurement is blank");
            return false;
        }

        if (CollectionAssist.isEmpty(fields)) {
            logger.info("fields is empty");
            return false;
        }

        return true;
    }
}

class TagKeyValue {
    String key;

    String value;

    public TagKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}

class FieldKeyValue {
    String key;

    /**
     * Field字段的值规定为数值，如果是非数值，那么在转换成为LineProtocol字符串的时候需要加上“双引号”，否则会报“invalid boolean”错误
     */
    Number value;

    public FieldKeyValue(String key, Number value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
