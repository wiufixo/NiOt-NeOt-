package com.sist.nono.repository;

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
}
