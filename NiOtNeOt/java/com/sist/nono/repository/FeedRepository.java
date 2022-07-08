package com.sist.nono.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.Feed;

//DAO
//자동으로 bean등록이 되어 @Repository 생략가능
public interface FeedRepository extends JpaRepository<Feed, Integer> {

}
