package com.tintucgiaothong.data.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tintucgiaothong.data.domain.NguoiDung;

public interface NguoiDungRepo extends CrudRepository<NguoiDung, Long>{
	Optional<NguoiDung> findByUsername(String username);
	Optional<NguoiDung> findByEmail(String email);
}
