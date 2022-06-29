package com.sist.nono.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Board;
import com.sist.nono.model.User;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
