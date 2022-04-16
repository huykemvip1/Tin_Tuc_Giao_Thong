package com.tintucgiaothong.data.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tintucgiaothong.data.Repository.NguoiDungRepo;
import com.tintucgiaothong.data.Repository.NoiDungRepo;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;

@Service
@Transactional
public class CheckUserServiceImp implements CheckUserService{
	@Autowired
	private NoiDungRepo noiDungRepo;
	@Autowired
	private NguoiDungRepo nguoiDungRepo;
	@Override
	public List<NoiDung> getAllByUsername(String username) {
		Optional<NguoiDung> nguoiDung=nguoiDungRepo.findByUsername(username);
		if (nguoiDung.isEmpty()) {
			return null;
		}else {
			List<NoiDung> noiDungs=noiDungRepo.findNoiDungByUser(nguoiDung.get());
			return noiDungs;
		}
	}

	@Override
	public boolean deleteNoiDung(long id) {
		Optional<NoiDung> noiDung=noiDungRepo.findById(id);
		if (noiDung.isEmpty()) {
			return false;
		}else {
			noiDungRepo.delete(noiDung.get());
			noiDungRepo.resetID();
			return true;
		}
		
	}

	@Override
	public NguoiDung updateUser(NguoiDung nguoiDung) {
		Optional<NguoiDung> findND=nguoiDungRepo.findByUsername(nguoiDung.getUsername());
		if (findND.isEmpty()) {
			return null;
		}else {
			
			if (nguoiDung.getEmail() == null || nguoiDung.getEmail().equals("")) {
				return null;
			}
			if(nguoiDung.getPassword() == null || nguoiDung.getPassword().equals("")) {
				return null;
			}
			if(nguoiDung.getSex() == null || nguoiDung.getSex().equals("")) {
				return null;
			}
		
			
			nguoiDung.setVaiTro(findND.get().getVaiTro());
			nguoiDung.setId(findND.get().getId());
			nguoiDungRepo.save(nguoiDung);
			return nguoiDung;
		}
	}

	@Override
	public NguoiDung findNguoiDung(String username) {
		Optional<NguoiDung> findND=nguoiDungRepo.findByUsername(username);
		if(findND.isEmpty()) {
			return null;
		}else {
			return findND.get();
		}
		
	}

}
