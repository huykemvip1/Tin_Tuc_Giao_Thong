package com.tintucgiaothong.data.service;

import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tintucgiaothong.data.Repository.MaTheoDoiRepo;
import com.tintucgiaothong.data.Repository.NguoiDungRepo;
import com.tintucgiaothong.data.domain.MaTheoDoi;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.VaiTro;

import lombok.extern.slf4j.Slf4j;


@Service

@Transactional
@Slf4j
public class NoCheckServiceImp implements NoCheckService{
	@Autowired
	private NguoiDungRepo nguoiDungRepo;
	@Autowired
	private MaTheoDoiRepo maTheoDoiRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public MaTheoDoi checkUser(String username, String password) {
		
		Optional<NguoiDung> nguoiDung=nguoiDungRepo.findByUsername(username);
		
		if (nguoiDung.isEmpty()) {
			return null;
		}
		if(nguoiDung.get().getPassword().equals(password)) {
			
			return createCode(nguoiDung.get());
		}else {
			return null;
		}
	}

	@Override
	public boolean addUser(NguoiDung nguoiDung) {
		Optional<NguoiDung> checkND=nguoiDungRepo.findByUsername(nguoiDung.getUsername());
		if(checkND.isEmpty()) {
			VaiTro vaiTro=new VaiTro();
			vaiTro.setId(3);
			vaiTro.setTen("user");
			nguoiDung.setVaiTro(vaiTro);
			nguoiDungRepo.save(nguoiDung);
			return true;
		}
		return false;
	}
	// ---- tao ma theo doi moi
	private MaTheoDoi createCode(NguoiDung nguoiDung) {
		// --- Tao chuoi random
		int min=97;
		int max=122;
		StringBuilder chuoi=new StringBuilder(6);
		Random random=new Random();
		for (int i=0 ; i< 6;i++) {
			int kytu=(int)(random.nextDouble() * (max-min))+min;
			chuoi.append((char) kytu);
		}
		
		MaTheoDoi maTheoDoi=new MaTheoDoi();
		maTheoDoi.setUsername(nguoiDung.getUsername());
		maTheoDoi.setNguoiDung(nguoiDung);
		maTheoDoi.setCode(chuoi.toString());
		
		return maTheoDoiRepo.save(maTheoDoi);
	}

	@Override
	public void checkMaTheoDoi(String username) {
		Optional<MaTheoDoi> maTheoDois=maTheoDoiRepo.findByUsername(username);
		if (maTheoDois.isPresent()) {
			maTheoDoiRepo.delete(maTheoDois.get());
		}
		maTheoDoiRepo.resetId();
	}

	@Override
	public String findForgotAccount(NguoiDung nguoiDung) {
		
		Optional<NguoiDung> findND=nguoiDungRepo.findByUsername(nguoiDung.getUsername());
		Optional<NguoiDung> findEmail=nguoiDungRepo.findByEmail(nguoiDung.getEmail());
		
		if (findND.isEmpty()) {
			return "UNE"; //------------  ki hieu username nay khong ton tai
		}else {
			
			if (findEmail.isEmpty() || findND.get() != findEmail.get()) {
				return "ENE"; // -------------------- ki hieu email khong khop voi tai khoan
			}else {
				sendPassword(findND.get());
				return "OK"; //------------------ Gui mat khau ve email thanh cong
			}
		}
	}
	private void sendPassword(NguoiDung nguoiDung) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(nguoiDung.getEmail());
		mailMessage.setText("Password old :\n"+"------------------- :: ----------"+nguoiDung.getPassword());
		mailMessage.setSubject("Success !!!! -- Send Password");
		mailMessage.setFrom("huyjavacode@gmail.com");
		
		javaMailSender.send(mailMessage);
	}
}
