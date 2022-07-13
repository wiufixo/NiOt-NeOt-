package com.sist.nono.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.FeedComment;
//T:물리적인 테이블이름(클래스이름) ,ID: Primary key(자료형)
public interface FeedCommentRepository extends JpaRepository<FeedComment, Integer> {

  

}
