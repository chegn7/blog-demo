package xyz.cheng7.blog;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = BlogApp.class)
public class KafkaTest {

    @Autowired
    KafkaProducer producer;
    @Autowired
    KafkaConsumer consumer;

    @Test
    public void testKafka() throws InterruptedException {
        producer.sendMessage("test", "测试消息1");
        producer.sendMessage("test", "测试消息2");
        producer.sendMessage("test", "测试消息3");
        Thread.sleep(1000 * 10);

    }
}

@Component
class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic, String content) {
        kafkaTemplate.send(topic, content);
    }

}



@Component
class KafkaConsumer {
    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record) {
        System.out.println(record.value());
    }
}