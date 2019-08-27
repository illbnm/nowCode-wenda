package com.nowcoder.async;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存事件发生的现场
 */
public class EventModel {
    private EventType type;
    private int actorId; //触发者
    private int entityType;
    private int entityId;
    private int entityOwnerId; // 拥有者
    private Map<String, String> exts = new HashMap<>();

    public EventModel() {
    }

    public EventModel setExt(String key, String map) {
        exts.put(key, map);
        return this;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;

    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;

    }


    public EventModel(EventType Type) {
        this.type = Type;

    }
}
