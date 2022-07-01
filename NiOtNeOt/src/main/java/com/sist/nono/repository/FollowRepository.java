package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nono.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
