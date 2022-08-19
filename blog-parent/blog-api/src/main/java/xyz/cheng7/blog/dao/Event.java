package xyz.cheng7.blog.dao;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Event {
    private String topic;
    // 事件的触发者id
    private Long creatorId;
    // 事件的触发者类型，是文章，评论，还是用户
    private Integer creatorType;
    // 事件目标对象的id
    private Long entityId;
    // 事件目标对象的类型
    private Integer entityType;
    private Long entityUserId;
    private Map<String, Object> data = new HashMap<>();

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public Event setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Event setCreatorType(Integer creatorType) {
        this.creatorType = creatorType;
        return this;
    }

    public Event setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public Event setEntityType(Integer entityType) {
        this.entityType = entityType;
        return this;
    }

    public Event setEntityUserId(Long entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Event setData(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
