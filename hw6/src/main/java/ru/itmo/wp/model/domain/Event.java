package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event {
    private Long id, userId;
    private EventType type;
    private Date creationTime;

    public Event() {}

    public Event(Long id, Long userId, EventType type, Date creationTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
