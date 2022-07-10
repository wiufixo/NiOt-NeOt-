package com.sist.nono.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nono.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage	, Integer> {
	public List<ProductImage> findAllByProduct(int pr_no);
	
}
