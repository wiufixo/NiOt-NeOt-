package com.sist.nono.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Feed;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface FeedRepository extends JpaRepository<Feed, Integer> {

	@Modifying
	@Query(value = "update feed set f_hit=f_hit+1 where f_no=?1", nativeQuery = true)
	public void increaseHit(int f_no);
	
	@Query(value = "select count(*) from feed order by f_no desc", nativeQuery = true)
	public int getTotalRecordCnt();
	
	@Query(value = "select * from feed where cu_no=?1", nativeQuery = true)
	public List<Feed> findByCu_no(int cu_no);
}
