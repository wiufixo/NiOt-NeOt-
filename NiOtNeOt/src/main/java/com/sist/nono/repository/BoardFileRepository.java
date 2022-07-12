package com.sist.nono.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Integer> {
	
	@Query(value = "select * from boardFile where b_no=?1", nativeQuery = true)
	public List<BoardFile> findAllByBoard(int b_no);
	
	@Query(value = "select count(*) from boardFile where b_no=?1", nativeQuery = true)
	public int totalFile(int b_no);
	
	@Modifying
	@Query(value = "delete from boardFile where b_no=?1", nativeQuery = true)
	public int deleteFile(int b_no);
	

}
