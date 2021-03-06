package com.tintucgiaothong.data.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.tintucgiaothong.data.Repository.ChuDeRepo;
import com.tintucgiaothong.data.Repository.NguoiDungRepo;
import com.tintucgiaothong.data.Repository.NoiDungRepo;
import com.tintucgiaothong.data.domain.ChuDe;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class CheckServiceImp implements CheckService{
	@Autowired
	private NoiDungRepo noiDungRepo;
	@Autowired
	private ChuDeRepo chuDeRepo;
	@Autowired
	private NguoiDungRepo nguoiDungRepo;
	@Override
	public List<NoiDung> getDataTwoTHGT() {
		Optional<ChuDe> chuDe=chuDeRepo.findByTen("Tinh Hinh Giao Thong");
		
			List<NoiDung> noiDungs=noiDungRepo.findNoiDungByChuDe(chuDe.get());
			return noiDungs;
		
		}
	@Override
	public List<NoiDung> getDataTwoTTGT() {
		Optional<ChuDe> chuDe=chuDeRepo.findByTen("Tin Tuc Giao Thong");
		
			List<NoiDung> noiDungs=noiDungRepo.findNoiDungByChuDe(chuDe.get());
			
			return noiDungs;
	
		}
	
	@Override
	public boolean insertData(NoiDung noiDung) {
		String ten=noiDung.getChuDe().getTen();
		Optional<ChuDe> chuDe=chuDeRepo.findByTen(ten);
		String username=noiDung.getNguoiDung().getUsername();
		Optional<NguoiDung> nguoiDung=nguoiDungRepo.findByUsername(username);
		if (chuDe.isEmpty() || nguoiDung.isEmpty()) {
			return false;
		}else {
			noiDung.setCreation_time(LocalDateTime.now());
			noiDung.setChuDe(chuDe.get());
			noiDung.setNguoiDung(nguoiDung.get());
			noiDungRepo.save(noiDung);
			return true;
		}
	}
	@Override
	public NoiDung getOne(long id) {
		Optional<NoiDung> noiDung=noiDungRepo.findById(id);
		if (noiDung.isEmpty()) {
			return null;
		}else {
			return noiDung.get();
		}
		
	}
	
}
