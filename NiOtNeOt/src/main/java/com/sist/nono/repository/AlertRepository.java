package com.sist.nono.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nono.model.Alert;
import com.sist.nono.model.User;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능 
public interface AlertRepository extends JpaRepository<Alert, Integer>{
	@Query(value = "select COUNT(*) "
			+"from alert where cu_no=:cu_no and al_checked=0", nativeQuery = true)
	int findUncheckedAlert(@Param("cu_no") int cu_no);
	
	@Query(value = "select * from alert where cu_no=:cu_no", nativeQuery = true)
	List<Alert> findAllByCu_no(@Param("cu_no") int cu_no);
}                                                                         