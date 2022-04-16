package com.tintucgiaothong.data.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;
import com.tintucgiaothong.data.service.CheckUserService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/ttgt")
@Slf4j
public class CheckUser {
	@Autowired
	private CheckUserService checkUserService;
	
	@GetMapping("/getAll/{username}")
	public ResponseEntity<List<NoiDung>> getAll(@PathVariable("username") String username,HttpServletRequest request){
			List<NoiDung> noiDungs=checkUserService.getAllByUsername(username);
			if (noiDungs == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(noiDungs);
			
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteId(@PathVariable("id") long idNoiDung,HttpServletRequest request){
		log.info("ok -----------------");
		boolean check= checkUserService.deleteNoiDung(idNoiDung);
		if (check == true) {
		     return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	@PutMapping("/update/{username}")
	@ResponseBody
	public ResponseEntity<NguoiDung> update(@RequestBody NguoiDung nguoiDung,HttpServletRequest request){
		log.info(nguoiDung.getBirthDay().toString());
		NguoiDung result=checkUserService.updateUser(nguoiDung);
		if (result == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	@GetMapping("/getDetails/{username}")
	public ResponseEntity<NguoiDung> getDetails(@PathVariable("username") String username,HttpServletRequest request){
		NguoiDung result=checkUserService.findNguoiDung(username);
			if (result == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok(result);
			
	}
}
