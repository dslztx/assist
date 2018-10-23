package me.dslztx.assist.client.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

public class KafkaConsumerFactory {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerFactory.class);

    private static final String CONFIG_FILE = "kafka.properties";

    public synchronized static KafkaConsumer<String, byte[]> obtainKafkaConsumer() {
        try {
            Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

            String servers = configuration.getString("kafka.servers");

            if (StringUtils.isBlank(servers)) {
                throw new RuntimeException("no kafka servers");
            }

            Map<String, Object> props = new HashMap<String, Object>();

            props.put("bootstrap.servers", servers);
            props.put("group.id", "defaultGroup");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("fetch.max.bytes", 100 * 1024 * 1024);

            return new KafkaConsumer<String, byte[]>(props, new StringDeserializer(), new ByteArrayDeserializer());
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public synchronized static KafkaConsumer<String, byte[]> obtainKafkaConsumer(String groupId) {
        try {
            if (StringAssist.isBlank(groupId)) {
                throw new RuntimeException("groupId is null");
            }

            Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

            String servers = configuration.getString("kafka.servers");

            if (StringUtils.isBlank(servers)) {
                throw new RuntimeException("no kafka servers");
            }

            Map<String, Object> props = new HashMap<String, Object>();

            props.put("bootstrap.servers", servers);
            props.put("group.id", groupId);
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("fetch.max.bytes", 100 * 1024 * 1024);

            return new KafkaConsumer<String, byte[]>(props, new StringDeserializer(), new ByteArrayDeserializer());
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

}
