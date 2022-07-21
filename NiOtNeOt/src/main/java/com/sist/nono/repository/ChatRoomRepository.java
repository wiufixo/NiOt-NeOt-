package com.sist.nono.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sist.nono.model.ChatRoom;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer>{
	@Query(value = "select * from chatroom where cu_no=:cu_no or bcu_no=:cu_no", nativeQuery = true)
	List<ChatRoom> findAllByCu_no(@Param("cu_no") int cu_no);
	
	@Transactional
	@Modifying
	@Query(value = "insert into chatroom(cu_no, bcu_no, pr_no, cr_created,trade) values(?1, ?2, ?3, now(),0)", nativeQuery = true)
	void createChatRoom(int cu_no, int bcu_no, int pr_no);
	
	@Query(value = "select cu.cu_id from chatroom cr, customer cu where cr.bcu_no = cu.cu_no and cr_no=:cr_no", nativeQuery = true)
	String findBcuidByCrno(int cr_no);
}                                                                         