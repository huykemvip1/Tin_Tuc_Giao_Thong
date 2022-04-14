package com.tintucgiaothong.data.Repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tintucgiaothong.data.domain.ChuDe;


public interface ChuDeRepo extends CrudRepository<ChuDe, Integer>{
	 Optional<ChuDe> findByTen(String ten);
}
