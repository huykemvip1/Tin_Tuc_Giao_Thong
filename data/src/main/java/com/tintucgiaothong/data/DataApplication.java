package com.tintucgiaothong.data;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.tintucgiaothong.data.Repository.ChuDeRepo;
import com.tintucgiaothong.data.Repository.NguoiDungRepo;
import com.tintucgiaothong.data.domain.ChuDe;
import com.tintucgiaothong.data.domain.MaTheoDoi;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;

import lombok.Data;


@SpringBootApplication
@EnableAspectJAutoProxy
public class DataApplication implements CommandLineRunner{
	@Autowired
	private NguoiDungRepo nguoiDungRepo;
	@Autowired
	private ChuDeRepo chuDeRepo;
	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		/*
		String url = "http://localhost:8080/api/ttgt/getAll/huyhomhinh";
		HttpHeaders headers = new HttpHeaders();
		headers.set("code","fwcwb");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<NoiDung[]> responseEntity=restTemplate.getForEntity(url,NoiDung[].class,entity);
		System.out.print(responseEntity.getStatusCodeValue());
		*/
		
		
		
		/*
		String url = "http://localhost:8080/api/ttgt/login";
		HttpHeaders headers = new HttpHeaders();
		headers.set("username","vanhiep");
		headers.set("password","vanhiep");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<MaTheoDoi> responseEntity=restTemplate.exchange(url,HttpMethod.GET,entity,MaTheoDoi.class);
		System.out.print(responseEntity.getStatusCodeValue());
		*/
		// ----------------
		
		
		
		
		String url = "http://localhost:8080/api/ttgt/add";
		HttpHeaders headers = new HttpHeaders();
		headers.set("code","cukqif");
		
		RestTemplate restTemplate=new RestTemplate();
		// ----- set chude
		ChuDe chuDe=new ChuDe();
		chuDe.setTen("Tinh Hinh Giao Thong");
		// ----- set nguoidung
		NguoiDung nguoiDung=new NguoiDung();
		nguoiDung.setUsername("huyhomhinh");
		NoiDung noiDung=new NoiDung();
		// -----  set imag
		File file=new File("F://Tin tuc giao thong/3.jpg");
		BufferedImage bufferedImage=ImageIO.read(file);
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
		byte[] bytes=byteArrayOutputStream.toByteArray();
		String image=Base64.encodeBase64String(bytes);
		noiDung.setChuDe(chuDe);
		noiDung.setContent("Đây là ở Giải Phóng  đang thông thoáng \n Các bạn nên chọn cung đường này để đi");
		noiDung.setNguoiDung(nguoiDung);
		noiDung.setImage(image);
		HttpEntity<NoiDung> entity = new HttpEntity<NoiDung>(noiDung, headers);
		ResponseEntity<MaTheoDoi> responseEntity=restTemplate.exchange(url,HttpMethod.POST,entity,MaTheoDoi.class);
		System.out.print(responseEntity.getStatusCodeValue());
		
		
		
		/*
		String url = "http://localhost:8080/api/ttgt/all/ttgt/0";
		HttpHeaders headers = new HttpHeaders();
		headers.set("code","hgueqk");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<NoiDung[]> responseEntity=restTemplate.exchange(url,HttpMethod.GET,entity,NoiDung[].class);
		System.out.print(responseEntity.getBody()[0]);
		*/
		
	}
	
}