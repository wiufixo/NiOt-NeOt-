package com.sist.nono.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer> {

	@Query(value = "select * from boardComment where b_no=?1 order by bc_no desc", nativeQuery = true)
	public List<BoardComment> findAllByNo(int b_no);
	
	@Modifying
	@Query(value = "update boardComment set bc_content=?1, bc_updated=now() where bc_no=?2", nativeQuery = true)
	public int update(String bc_content, int bc_no);

	@Query(value = "select count(*) from boardComment where b_no=?1", nativeQuery = true)
	public int getTotByNo(int b_no);
	
}
