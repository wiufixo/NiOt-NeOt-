package com.sist.nono.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Wish;


public interface WishRepository extends JpaRepository<Wish, Integer> {
	@Query(value = "select count(*) from wish where cu_no=?1",nativeQuery = true)
	int countWish(int cu_no);
}
