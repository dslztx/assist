package me.dslztx.assist.client.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerFactoryTest {

  private static final Logger logger = LoggerFactory.getLogger(KafkaProducerFactoryTest.class);

  @Test
  public void obtainKafkaProducer() throws Exception {
    try {
      KafkaProducer<String, byte[]> kafkaProducer = KafkaProducerFactory.obtainKafkaProducer();
      Assert.assertNotNull(kafkaProducer);
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

}