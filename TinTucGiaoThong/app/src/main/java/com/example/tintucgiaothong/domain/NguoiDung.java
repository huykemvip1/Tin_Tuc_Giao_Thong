package com.example.tintucgiaothong.domain;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;


public class NguoiDung {
    public NguoiDung(){}
    private long id;
    private String username;
    private String password;
    private String fullname;
    private LocalDate birthDay;
    private String sex;

    public NguoiDung(long id, String username, String password, String fullname, LocalDate birthDay, String sex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.birthDay = birthDay;
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
