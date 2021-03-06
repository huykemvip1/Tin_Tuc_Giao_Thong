package com.tintucgiaothong.data.Repository;


import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tintucgiaothong.data.domain.MaTheoDoi;
public interface MaTheoDoiRepo extends CrudRepository<MaTheoDoi, Long>{
	@Modifying
	@Query(nativeQuery = true,value = "alter table matheodoi auto_increment = 0")
	void resetId();
	List<MaTheoDoi> findAll();
	Optional<MaTheoDoi> findByCode(String code);
	Optional<MaTheoDoi> findByUsername(String username);
	
}
