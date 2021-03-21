package com.example.collegeadminapp.notice;

import java.util.ArrayList;

public class NoticeData {
    String title, description, date, time, key;
    ArrayList<String> imageUrls;


    public NoticeData() {
    }


    public NoticeData(String title, String description, ArrayList<String> imageUrls, String date, String time, String key) {
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
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

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
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
