package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.LoginList;


public interface LoginListRepository extends JpaRepository<LoginList,Integer > {
}
