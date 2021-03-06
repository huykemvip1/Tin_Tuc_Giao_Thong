package com.tintucgiaothong.data.aop;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tintucgiaothong.data.Repository.MaTheoDoiRepo;
import com.tintucgiaothong.data.domain.MaTheoDoi;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class CheckAspect {
	@Autowired
	private MaTheoDoiRepo maTheoDoiRepo;
	@Around(" execution(* com.tintucgiaothong.data.resource.CheckUser.*(..)) && args(..,request)")
	public Object check_User(ProceedingJoinPoint proceedingJoinPoint,HttpServletRequest request) throws Throwable {
		
		Optional<MaTheoDoi> maTheoDoi=maTheoDoiRepo.findByCode(request.getHeader("code"));
		log.info("haah" +request.getHeader("code"));
		if (maTheoDoi.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}else {
			Object noiDungs=proceedingJoinPoint.proceed();
			return noiDungs;
		}
	}
	@Around(" execution(* com.tintucgiaothong.data.resource.CheckBefore.*(..)) && args(..,request)")
	public Object check_Befor(ProceedingJoinPoint proceedingJoinPoint,HttpServletRequest request) throws Throwable {
		
		Optional<MaTheoDoi> maTheoDoi=maTheoDoiRepo.findByCode(request.getHeader("code"));
		log.info("haah" +request.getHeader("code"));
		if (maTheoDoi.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}else {
			Object noiDungs=proceedingJoinPoint.proceed();
			return noiDungs;
		}
	}
}
