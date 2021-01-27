package com.example.collegeadminapp;

public class NoticeData {
    String title, description, imageUrl, date, time, key;

    public NoticeData() {
    }


    public NoticeData(String title, String description, String imageUrl, String date, String time, String key) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
