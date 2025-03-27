package me.dslztx.assist.client.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dslztx.assist.util.ConfigLoadAssist;
import me.dslztx.assist.util.StringAssist;

public class KafkaProducerFactory {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerFactory.class);

    private static final String CONFIG_FILE = "kafka.properties";

    private static volatile boolean init = false;

    private static KafkaProducer<String, byte[]> kafKaProducer;

    public static KafkaProducer<String, byte[]> obtainKafkaProducer() {
        if (!init) {
            init();
        }

        return kafKaProducer;
    }

    private static void init() {
        if (!init) {
            synchronized (KafkaProducerFactory.class) {
                if (!init) {
                    try {

                        Configuration configuration = ConfigLoadAssist.propConfig(CONFIG_FILE);

                        String servers = configuration.getString("kafka.servers");

                        if (StringUtils.isBlank(servers)) {
                            throw new RuntimeException("no kafka servers");
                        }

                        Map<String, Object> props = new HashMap<String, Object>();

                        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
                        props.put(ProducerConfig.ACKS_CONFIG, "all");
                        props.put(ProducerConfig.RETRIES_CONFIG, 3);
                        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 5 * 1024 * 1024);
                        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
                        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 100 * 1024 * 1024);
                        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 100 * 1024 * 1024);
                        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");
                        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,
                            "org.apache.kafka.clients.producer.RoundRobinPartitioner");

                        String username = configuration.getString("kafka.servers.username");
                        String password = configuration.getString("kafka.servers.password");

                        if (StringAssist.isNotBlank(username) && StringAssist.isNotBlank(password)) {
                            props.put("security.protocol", "SASL_PLAINTEXT");
                            props.put("sasl.mechanism", "PLAIN");

                            String auth = StringAssist.format(
                                "org.apache.kafka.common.security.plain"
                                    + ".PlainLoginModule required username=\"{}\" password=\"{}\";",
                                username, password);

                            props.put("sasl.jaas.config", auth);
                        }

                        kafKaProducer =
                            new KafkaProducer<String, byte[]>(props, new StringSerializer(), new ByteArraySerializer());

                    } catch (Exception e) {
                        logger.error("", e);
                        throw new RuntimeException(e);
                    } finally {
                        init = true;
                    }
                }
            }
        }
    }
}
