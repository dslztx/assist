package me.dslztx.assist.client.kafka;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Configurations configs = new Configurations();

            Configuration configuration = configs.properties(new File(CONFIG_FILE));

            String servers = configuration.getString("kafka.servers");

            if (StringUtils.isBlank(servers)) {
              throw new RuntimeException("no kafka servers");
            }

            Map<String, Object> props = new HashMap<String, Object>();

            props.put("bootstrap.servers", servers);
            props.put("acks", "all");
            props.put("retries", 3);
            props.put("batch.size", 16384);
            props.put("linger.ms", 0);
            props.put("max.request.size", 100 * 1024 * 1024);
            props.put("buffer.memory",100 * 1024 * 1024);

            kafKaProducer = new KafkaProducer<String, byte[]>(props, new StringSerializer(),
                new ByteArraySerializer());
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
