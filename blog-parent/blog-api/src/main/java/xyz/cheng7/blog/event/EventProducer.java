package xyz.cheng7.blog.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import xyz.cheng7.blog.dao.Event;
import xyz.cheng7.blog.util.JSONUtil;

@Component
public class EventProducer {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void fireEvent(Event event) {
        kafkaTemplate.send(event.getTopic(), JSONUtil.getInstance().toJSON(event));
    }
}
