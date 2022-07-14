package com.sist.nono.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.sist.nono.model.FeedImg;

public interface FeedImgRepository extends JpaRepository<FeedImg, Integer> {

	//해당 게시물에 담긴 피드 이미지의 정보를 여러개 를 가져와야 하기 때문에 List<FeedImg> 를 선언해준다
	// 여기서 f_no=?1 은 매개변수의 순서를 의미 (f_n0)
	@Query(value = "select * from feedImg where f_no=?1", nativeQuery = true)
	public List<FeedImg> findAllByFeed(int f_no);
	
	//해당 게시물의 담긴 피드 자료의 총 갯수를카운트 해준다
	//갯수로 반환 되는 값은 정수 값을 반환 하므로 int 형 으로 선언
	@Query(value = "select count(*) from feedImg where f_no=?1", nativeQuery = true)
	public int totalFile(int f_no);
	
	
	//해당 게시물의 이미지 파일을 모두 삭제
	@Modifying
	@Query(value = "delete from feedImg where f_no=?1", nativeQuery = true)
	public int deleteFile(int f_no);
}
