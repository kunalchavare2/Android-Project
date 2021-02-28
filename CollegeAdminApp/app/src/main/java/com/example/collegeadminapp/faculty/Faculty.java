package com.example.collegeadminapp.faculty;

public class Faculty {
    private String name,email,description,department,imageUrl,key;
    public Faculty() {
    }

    public Faculty(String name, String email, String description, String department, String imageUrl, String key) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.department = department;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
