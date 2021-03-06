package com.tintucgiaothong.data.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tintucgiaothong.data.domain.NoiDung;
import com.tintucgiaothong.data.service.CheckService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ttgt")
@Slf4j
public class CheckBefore {
	@Autowired
	private CheckService checkService;
	@GetMapping("/all/thgt") //---- dua hai du lieu ra tinh hinh giao thong
	@ResponseBody
	public ResponseEntity<List<NoiDung>> getDataTHGT(HttpServletRequest request){
			return ResponseEntity.ok(checkService.getDataTwoTHGT());
	}
	@GetMapping("/all/ttgt") //---- dua hai du lieu ra tin tuc giao thong
	@ResponseBody
	public ResponseEntity<List<NoiDung>> getDataTTGT(HttpServletRequest request){
			return ResponseEntity.ok(checkService.getDataTwoTTGT());
	}
	@PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE})
	@ResponseBody
	public ResponseEntity<NoiDung> addData(@RequestBody NoiDung noiDung,HttpServletRequest request){
		log.info(noiDung.getContent()+"hahahah--------------");
		boolean check=checkService.insertData(noiDung);
		
		if (check == true) {
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}
	@GetMapping("/get/{id}")
	public ResponseEntity<NoiDung> getOne(@PathVariable("id") long id,HttpServletRequest request){
		NoiDung noiDung=checkService.getOne(id);
		if (noiDung == null) {
			return ResponseEntity.badRequest().build();
		}else {
			return ResponseEntity.ok(noiDung);
		}
	}
	@Data
	@AllArgsConstructor
	static class FindNoiDung {
		private long id;
		private String chude;
	}
}
