package com.sist.nono.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sist.nono.model.Alert;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능 
public interface AlertRepository extends JpaRepository<Alert, Integer>{
	@Query(value = "select COUNT(*) "
			+"from alert where cu_no=?1 and al_checked is null", nativeQuery = true)
	int findUncheckedAlert(int cu_no);
	
	@Query(value = "select * from alert where cu_no=?1", nativeQuery = true)
	List<Alert> findAllByCu_no(int cu_no);
}                                                                         