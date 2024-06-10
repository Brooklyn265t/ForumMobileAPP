package com.example.forum;

public class Topic {
    private String title;
    private String description;
    private String username;
    private String data;
    public Topic(String title, String description, String username, String data) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.data = data;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getUsername() {
        return username;
    }
    public String getData() {
        return data;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setData(String data) {
        this.data = data;
    }
}