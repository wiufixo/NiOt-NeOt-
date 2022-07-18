package com.sist.nono.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	@Modifying
	@Query(value = "update board set b_title=?1, b_content=?2, b_update=now() where b_no=?3", nativeQuery = true)
	public int update(String b_title, String b_content, int b_no);
	
	@Query(value = "select * from board order by b_no desc", nativeQuery = true)
	public List<Board> selectAll();
	
	@Modifying
	@Query(value = "update board set b_hit=b_hit+1 where b_no=?1", nativeQuery = true)
	public void increaseHit(int b_no);
	
	@Query(value = "select * from board where cu_no=?1", nativeQuery = true)
	public List<Board> findAllByCu_no(int user_no);
}
