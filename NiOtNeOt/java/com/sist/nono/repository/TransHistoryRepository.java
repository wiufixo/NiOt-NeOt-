package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.TransHistory;

public interface TransHistoryRepository extends JpaRepository<TransHistory, Integer> {
	
	@Query(value = "select count(*) from transhistory where cu_no=?1", nativeQuery = true)
	int countTransHistory(int cu_no);
}
