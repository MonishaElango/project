package com.hertzai.hevolve.gson;

public class BottomTab {
    private Integer image;
    private String title;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BottomTab(Integer image, String title) {
        this.image = image;
        this.title = title;
    }
}
