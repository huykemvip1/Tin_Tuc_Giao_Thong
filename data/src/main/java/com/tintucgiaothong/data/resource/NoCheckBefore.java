package com.tintucgiaothong.data.resource;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tintucgiaothong.data.domain.MaTheoDoi;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.service.NoCheckService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/ttgt")
@Slf4j
public class NoCheckBefore {
	@Autowired
	private NoCheckService noCheckService;
	@GetMapping("/login")
	public ResponseEntity<MaTheoDoi> login(HttpServletRequest httpServletRequest){
		
		String username=httpServletRequest.getHeader("username");
		String password=httpServletRequest.getHeader("password");
		log.info(password);
		noCheckService.checkMaTheoDoi(username);
		MaTheoDoi check= noCheckService.checkUser(username, password);
		if(check != null) {
			return ResponseEntity.ok(check);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity<NguoiDung> register(@RequestBody NguoiDung_add nguoiDung){
		NguoiDung ngDung=new NguoiDung();
		ngDung.setEmail(nguoiDung.getEmail());
		ngDung.setPassword(nguoiDung.getPassword());
		ngDung.setSex(nguoiDung.getSex());
		ngDung.setUsername(nguoiDung.getUsername());
		
		
		
		boolean check=noCheckService.addUser(ngDung);
		if (check == true) {
			return ResponseEntity.ok(ngDung);
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	@PostMapping("/forgot")
	@ResponseBody
	public ResponseEntity<String> forgot (@RequestBody NguoiDung nguoiDung){
		String mess=noCheckService.findForgotAccount(nguoiDung);
		return ResponseEntity.ok(mess);
	}
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest httpServletRequest){
		noCheckService.checkMaTheoDoi(httpServletRequest.getHeader("username"));
		return ResponseEntity.ok().build();
	}
	@Data
	
	static class NguoiDung_add{
		private long id;
		private String username;
		private String password;
		private String sex;
		private String email;
		
	}
}
