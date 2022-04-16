package com.tintucgiaothong.data.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tintucgiaothong.data.domain.ChuDe;
import com.tintucgiaothong.data.domain.NguoiDung;
import com.tintucgiaothong.data.domain.NoiDung;

@Transactional
public interface NoiDungRepo extends CrudRepository<NoiDung, Long>{
	List<NoiDung> findAll();
	@Query("select n from NoiDung n where n.nguoiDung = :nguoiDung")
	List<NoiDung> findNoiDungByUser(@Param("nguoiDung") NguoiDung nguoiDung);

	@Query("select n from NoiDung n where n.chuDe = :chuDe order by n.creation_time desc ")
	List<NoiDung> findNoiDungByChuDe(@Param("chuDe") ChuDe chuDe);
	@Modifying
	@Query(nativeQuery = true,value = "alter table noidung auto_increment=0")
	public void resetID();
	
}
