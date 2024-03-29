package me.dslztx.assist.client.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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

            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "defaultGroup");
            // 这个参数十分重要，影响消费行为：https://blog.csdn.net/lishuangzhe7047/article/details/74530417
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

            // Consumer A调用poll(timeout)之后，其绑定到的分区组装返回数据：
            // - 单分区最多返回ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG字节数据
            // - 然后最多返回ConsumerConfig.MAX_POLL_RECORDS_CONFIG条Record
            // - 然后最终的字节大小又不能超过ConsumerConfig.FETCH_MAX_BYTES_CONFIG
            // 当在timeout时间内组装完成则顺利返回，否则超时本次poll未获取结果
            // 从以上可知，影响Consumer吞吐量的是以下3个参数+poll的timeout
            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 10 * 1024 * 1024);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "3000");
            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100 * 1024 * 1024);

            String username = configuration.getString("kafka.servers.username");
            String password = configuration.getString("kafka.servers.password");

            if (StringAssist.isNotBlank(username) && StringAssist.isNotBlank(password)) {
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put("sasl.mechanism", "PLAIN");

                String auth = StringAssist.format("org.apache.kafka.common.security.plain"
                    + ".PlainLoginModule required username=\"{}\" password=\"{}\";", username, password);

                props.put("sasl.jaas.config", auth);
            }

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

            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            // 这个参数十分重要，影响消费行为：https://blog.csdn.net/lishuangzhe7047/article/details/74530417
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");

            // Consumer A调用poll(timeout)之后，其绑定到的分区组装返回数据：
            // - 单分区最多返回ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG字节数据
            // - 然后最多返回ConsumerConfig.MAX_POLL_RECORDS_CONFIG条Record
            // - 然后最终的字节大小又不能超过ConsumerConfig.FETCH_MAX_BYTES_CONFIG
            // 当在timeout时间内组装完成则顺利返回，否则超时本次poll未获取结果
            // 从以上可知，影响Consumer吞吐量的是以下3个参数+poll的timeout
            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 10 * 1024 * 1024);
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "3000");
            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100 * 1024 * 1024);

            String username = configuration.getString("kafka.servers.username");
            String password = configuration.getString("kafka.servers.password");

            if (StringAssist.isNotBlank(username) && StringAssist.isNotBlank(password)) {
                props.put("security.protocol", "SASL_PLAINTEXT");
                props.put("sasl.mechanism", "PLAIN");

                String auth = StringAssist.format("org.apache.kafka.common.security.plain"
                    + ".PlainLoginModule required username=\"{}\" password=\"{}\";", username, password);

                props.put("sasl.jaas.config", auth);
            }

            return new KafkaConsumer<String, byte[]>(props, new StringDeserializer(), new ByteArrayDeserializer());
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

}
