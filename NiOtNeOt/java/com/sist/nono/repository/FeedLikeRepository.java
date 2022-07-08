package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.nono.model.FeedLike;
import com.sist.nono.model.FeedLike_View;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLike_View>{

}
