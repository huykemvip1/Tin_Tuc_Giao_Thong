package com.tintucgiaothong.data.service;

import java.util.List;

import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;

public interface CheckUserService {
	List<NoiDung> getAllByUsername(String username);// ---- In tat ca du lieu da post len
	boolean deleteNoiDung(long idNoiDung); // ---- Xoa noi dung muon xoa cua minh
	NguoiDung updateUser(NguoiDung nguoiDung);//------- cap nhap nguoi dung
	NguoiDung findNguoiDung(String username);// ----- tim tai khoan co ten username nhu ben
}
