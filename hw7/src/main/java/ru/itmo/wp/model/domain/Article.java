package ru.itmo.wp.model.domain;

import java.util.Date;

public class Article {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private Date creationTime;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    boolean hidden;

    public Article(Long id, Long userId, String title, String text, Date creationTime, boolean hidden) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime;
        this.hidden = hidden;
    }

    public Article() { }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
