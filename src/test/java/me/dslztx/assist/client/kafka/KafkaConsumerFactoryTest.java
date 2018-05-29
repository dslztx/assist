package me.dslztx.assist.client.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerFactoryTest {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerFactoryTest.class);

  @Test
  public void obtainKafkaConsumer() throws Exception {
    try {
      KafkaConsumer<String, byte[]> consumer1 = KafkaConsumerFactory.obtainKafkaConsumer();

      KafkaConsumer<String, byte[]> consumer2 = KafkaConsumerFactory.obtainKafkaConsumer();

      Assert.assertTrue(consumer1 != consumer2);
    } catch (Exception e) {
      logger.error("", e);
      Assert.fail();
    }
  }

}