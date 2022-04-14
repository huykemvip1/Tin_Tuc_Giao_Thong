package com.example.tintucgiaothong.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ChuDe {
    public ChuDe(){}
    private int id;
    private String ten;

    public ChuDe(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
