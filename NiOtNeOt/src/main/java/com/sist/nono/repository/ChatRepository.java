package com.sist.nono.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sist.nono.model.Chat;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능 
public interface ChatRepository extends JpaRepository<Chat, Integer> {
	@Query(value = "select count(*) " + "from chat where cu_no=:cu_no and ch_checked=0", nativeQuery = true)
	int findUnCheckedChat(@Param("cu_no") int cu_no);

	@Query(value = "select * from chat where cr_no=:cr_no", nativeQuery = true)
	List<Chat> findAllByCu_no(@Param("cr_no") int cr_no);
	
	@Transactional
	@Modifying
	@Query(value = "insert into chat(cu_no, cr_no, ch_content,ch_created) values(?1, ?2, ?3, now())", nativeQuery = true)
	void insertChat(int cu_no, int cr_no, String ch_content);
}