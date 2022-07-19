package com.sist.nono.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Wish;


public interface WishRepository extends JpaRepository<Wish, Integer> {
	@Query(value = "select count(*) from wish where cu_no=?1",nativeQuery = true)
	int countWish(int cu_no);
	
	@Query(value = "select * from wish where cu_no=?1",nativeQuery = true)
	ArrayList<Wish> findAllByCu_no(int cu_no);
	
	@Query(value = "select * from wish where pr_no=?1",nativeQuery = true)
	ArrayList<Wish> findAllByPr_no(int pr_no);
}
