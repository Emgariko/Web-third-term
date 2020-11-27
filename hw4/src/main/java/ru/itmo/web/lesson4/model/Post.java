package ru.itmo.web.lesson4.model;

public class Post {
    private final long id;

    private final long userId;
    private final String title, text;

    public Post(long id, long user_id, String title, String text) {
        this.id = id;
        this.userId = user_id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
