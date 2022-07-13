package com.sist.nono.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.sist.nono.model.FeedImg;

public interface FeedImgRepository extends JpaRepository<FeedImg, Integer> {

	
	@Query(value = "select * from feedImg where f_no=?1", nativeQuery = true)
	public List<FeedImg> findAllByFeed(int f_no);
	
	@Query(value = "select count(*) from feedImg where f_no=?1", nativeQuery = true)
	public int totalFile(int f_no);
	
	@Modifying
	@Query(value = "delete from feedImg where f_no=?1", nativeQuery = true)
	public int deleteFile(int f_no);
}
