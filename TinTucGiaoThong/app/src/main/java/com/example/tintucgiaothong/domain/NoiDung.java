package com.example.tintucgiaothong.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


public class NoiDung {
    public NoiDung(){}
    private long id;
    private String content;
    private String image;
    private String creation_time;
    private String username;
    private String chude;

    public NoiDung(long id, String content, String image, String creation_time, String username, String chude) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.creation_time = creation_time;
        this.username = username;
        this.chude = chude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChude() {
        return chude;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }
}
