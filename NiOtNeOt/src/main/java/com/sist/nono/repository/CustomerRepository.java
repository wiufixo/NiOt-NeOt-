package com.sist.nono.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sist.nono.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query(value = "select * from customer where cu_id=?1", nativeQuery = true)
	Optional<Customer> findByCu_id(String cu_id);
	
	@Query(value = "select * from customer where cu_email=?1", nativeQuery = true)
	Optional<Customer> findByCu_email(String cu_email);
	
	@Query(value = "select * from customer where cu_nickname=?1", nativeQuery = true)
	Optional<Customer> findByCu_nickname(String cu_nickname);
}
