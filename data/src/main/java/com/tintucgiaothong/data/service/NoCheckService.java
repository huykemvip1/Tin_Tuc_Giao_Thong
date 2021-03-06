package com.tintucgiaothong.data.service;

import com.tintucgiaothong.data.domain.MaTheoDoi;
import com.tintucgiaothong.data.domain.NguoiDung;

public interface NoCheckService {
	MaTheoDoi checkUser(String username,String password); // ------  kiem tra xem tai khoan mat khau dung khong
	boolean addUser(NguoiDung nguoiDung);  // them nguoi dung voi dieu kien nguoi dung chua ton tai
	void checkMaTheoDoi(String username); // ---- neu ton tai r thi phai xoa di
	String findForgotAccount(NguoiDung nguoiDung); // ---------------- Tim kiem tai khoan bi quen
}
