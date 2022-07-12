package com.sist.nono.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sist.nono.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage	, Integer> {
}
