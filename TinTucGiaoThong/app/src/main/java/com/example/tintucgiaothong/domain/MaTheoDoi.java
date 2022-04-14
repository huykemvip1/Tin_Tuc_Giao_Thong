package com.example.tintucgiaothong.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


public class MaTheoDoi {
    public MaTheoDoi(){}
    private long id;
    private String username;
    private String code;
    private NguoiDung nguoiDung;

    public MaTheoDoi(long id, String username, String code, NguoiDung nguoiDung) {
        this.id = id;
        this.username = username;
        this.code = code;
        this.nguoiDung = nguoiDung;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
