package com.sist.nono.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nono.model.Follow;
import com.sist.nono.model.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
	
	@Query(value = "select count(*) from follow where followed=?1", nativeQuery = true)
	int countFollower(int cu_no);
	
	@Query(value = "select count(*) from follow where follower=?1", nativeQuery = true)
	int countFollow(int cu_no);
	
	@Query(value = "select count(*) from follow where follower=?1 and followed=?2",nativeQuery = true)
	int checkFollow(int cu_no, int user_no);
	
	@Query(value = "select fo_no from follow where follower=?1 and followed=?2",nativeQuery = true)
	int findFo_no(int cu_no, int user_no);
	
	@Query(value = "select * from follow where follower=?1",nativeQuery = true)
	List<Follow> findByFollower(int cu_no);
	
	@Query(value = "select * from follow where followed=?1",nativeQuery = true)
	List<Follow> findByFollowed(int cu_no);
}
