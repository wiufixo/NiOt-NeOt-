package com.sist.nono.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.User;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value = "select * from user where cu_id=?1", nativeQuery = true)
	Optional<User> findByCu_id(String cu_id);
	
}

// JPA 네이밍 쿼리 전략
// select * from user where cu_id=?1 and cu_pwd=?2
//User findByCu_idAndCu_pwd(String cu_id, String cu_pwd); ===>밑기호때문에 이건 네이밍 처리가 안되는듯

//@Query(value = "select * from user where cu_id=?1 and cu_pwd=?2", nativeQuery = true)
//User login(String cu_id, String cu_pwd);