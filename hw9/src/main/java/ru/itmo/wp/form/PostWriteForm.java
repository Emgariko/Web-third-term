package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostWriteForm {
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 60)
    String title;
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 65000)
    String text;
    @NotNull
    @Size(min = 0, max=256)
    String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
