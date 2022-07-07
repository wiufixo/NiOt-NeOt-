package com.sist.nono.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Board;
import com.sist.nono.model.User;
import com.sist.nono.paging.PaginationInfo;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
	
	// !!!!!!!송승민이 만듬!!!!!!!!!
	@Query(value = "select * from board where cu_no=?1 order by b_created desc",nativeQuery = true)
	public List<Board> findAllByCu_no(int cu_no);
	//
	
	@Modifying
	@Query(value = "update board set b_title=?1, b_content=?2, b_update=now() where b_no=?3", nativeQuery = true)
	public int boardUpdate(String b_title, String b_content, int b_no);
	
	@Modifying
	@Query(value = "update board set b_hit=b_hit+1 where b_no=?1", nativeQuery = true)
	public void increaseHit(int b_no);
	
	@Query(value = "select count(*) from board order by b_no desc", nativeQuery = true)
	public int getTotalRecordCnt();
	
//	@Query(value = "select count(*) from board where b_title like concat('%', ?1, '%') or b_content like concat('%', ?1, '%') order by b_no desc", nativeQuery = true)
//	public int getTotalRecordCnt1(String searchKeyword);
//	
//	@Query(value = "select count(*) from board where b_title like concat('%', ?1, '%') order by b_no desc", nativeQuery = true)
//	public int getTotalRecordCnt2(String searchKeyword);
//	
//	@Query(value = "select count(*) from board where b_content like concat('%', ?1, '%') order by b_no desc", nativeQuery = true)
//	public int getTotalRecordCnt3(String searchKeyword);
//	
//	@Query(value = "select count(*) from board where b.cu_no=u.cu_no and u.cu_id like concat('%', ?1, '%') order by b.b_no desc", nativeQuery = true)
//	public int getTotalRecordCnt4(String searchKeyword);
//	
//	@Query(value = "select count(*) from board where b.cu_no=u.cu_no and bc.b_no=b.b_no and bc.bc_content like concat('%', ?1, '%') order by b.b_no desc", nativeQuery = true)
//	public int getTotalRecordCnt5(String searchKeyword);
	
	
	
	
	
	@Query(value = "select * from board order by b_no desc limit ?1, ?2", nativeQuery = true)
	public List<Board> selectBoardList(int startIndex, int pageSize);
	
//	@Query(value = "select * from board where b_title like concat('%', ?1, '%') and b_content like concat('%', ?1, '%') order by b_no desc limit ?2, ?3")
//	public List<Board> searchByTitleAndContent(String searchKeyword, int startIndex, int pageSize);
//	
//	@Query(value = "select * from board where b_title like concat('%', ?1, '%') order by b_no desc limit ?2, ?3")
//	public List<Board> searchByTitle(String searchKeyword, int startIndex, int pageSize);
//	
//	@Query(value = "select * from board where b_content like concat('%', ?1, '%') order by b_no desc limit ?2, ?3")
//	public List<Board> searchByContent(String searchKeyword, int startIndex, int pageSize);
//	
//	@Query(value = "select * from board b, user u where b.cu_no=u.cu_no and u.cu_id like concat('%', ?1, '%') order by b.b_no desc limit ?2, ?3")
//	public List<Board> searchByUser(String searchKeyword, int startIndex, int pageSize);
//	
//	@Query(value = "select * from board b, user u, boardComment bc where b.cu_no=u.cu_no and bc.b_no=b.b_no and bc.bc_content like concat('%', ?1, '%') order by b.b_no desc limit ?2, ?3")
//	public List<Board> searchByComment(String searchKeyword, int startIndex, int pageSize);
//	
}
