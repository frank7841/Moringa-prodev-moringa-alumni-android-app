package com.prodev.moringaalumni;

public class PostClient {
    private String title, description, imageUrl, username;

    public PostClient(String title, String description, String imgUrl, String username) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.username = username;
    }

    public PostClient(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imageUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
