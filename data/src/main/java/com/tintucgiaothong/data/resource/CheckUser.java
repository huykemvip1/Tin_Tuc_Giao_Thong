package com.tintucgiaothong.data.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tintucgiaothong.data.domain.NoiDung;
import com.tintucgiaothong.data.service.CheckUserService;


@RestController
@RequestMapping("/api/ttgt")
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
		return ResponseEntity.ok().build();
	}
}
