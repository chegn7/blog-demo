package xyz.cheng7.blog.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.util.JSONUtil;

@Component
@Slf4j
public class EventProducer {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void fireEvent(Event event) {
        log.info("---------------------尝试发送事件-------------------{}", event.getTopic());
        kafkaTemplate.send(event.getTopic(), JSONUtil.getInstance().toJSON(event));
    }
}
