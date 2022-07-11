package com.sist.nono.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Product;

import lombok.RequiredArgsConstructor;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query(value = "select * from product where cu_no=?1", nativeQuery = true)
	List<Product> findAllByCu_no(int cu_no);
	
}
