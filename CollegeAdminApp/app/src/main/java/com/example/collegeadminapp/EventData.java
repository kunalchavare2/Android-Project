package com.example.collegeadminapp;

import java.util.ArrayList;

public class EventData {
    String category, date, time, key;
    ArrayList<String> imageUrls;

    public EventData() {
    }

    public EventData(String category, String date, String time, String key, ArrayList<String> imageUrls) {
        this.category = category;
        this.date = date;
        this.time = time;
        this.key = key;
        this.imageUrls = imageUrls;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
