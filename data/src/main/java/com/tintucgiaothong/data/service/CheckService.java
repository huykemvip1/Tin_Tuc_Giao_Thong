package com.tintucgiaothong.data.service;

import java.util.List;

import com.tintucgiaothong.data.domain.NoiDung;

public interface CheckService {
	List<NoiDung> getDataTwoTHGT(); 
	List<NoiDung> getDataTwoTTGT();
	NoiDung getOne(long id);
	boolean insertData(NoiDung noiDung); // add du lieu noi dung vao database
}
