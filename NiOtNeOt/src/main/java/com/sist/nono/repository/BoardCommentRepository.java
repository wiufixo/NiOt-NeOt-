package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer>{
	
}
