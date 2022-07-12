package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer>{
	
	@Modifying
	@Query(value = "update boardComment set bc_content=?1, bc_updated=now() where bc_no=?2", nativeQuery = true)
	public void commentUpdate(String bc_content, int bc_no);
}
