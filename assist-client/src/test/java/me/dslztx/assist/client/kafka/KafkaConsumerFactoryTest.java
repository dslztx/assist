package me.dslztx.assist.client.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaConsumerFactoryTest {

    @Test
    public void obtainKafkaConsumer() {
        try {
            KafkaConsumer<String, byte[]> consumer1 = KafkaConsumerFactory.obtainKafkaConsumer();

            KafkaConsumer<String, byte[]> consumer2 = KafkaConsumerFactory.obtainKafkaConsumer();

            Assert.assertTrue(consumer1 != consumer2);

        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

    @Test
    @Ignore
    public void consumeTest() {
        try {

            KafkaConsumer<String, byte[]> kafkaConsumer = KafkaConsumerFactory.obtainKafkaConsumer();

            while (true) {
                try {
                    ConsumerRecords<String, byte[]> consumerRecords = kafkaConsumer.poll(10000);

                    boolean hasRecord = false;

                    for (ConsumerRecord<String, byte[]> consumerRecord : consumerRecords) {

                        hasRecord = true;

                        String key = consumerRecord.key();
                        byte[] value = consumerRecord.value();

                        log.info("the key is {}, the value is {}", key, value == null ? 0 : value.length);
                    }

                    if (!hasRecord) {
                        // 为了优化cpu
                        Thread.sleep(10);
                    }

                } catch (Throwable e) {
                    log.error("", e);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            Assert.fail();
        }
    }

}