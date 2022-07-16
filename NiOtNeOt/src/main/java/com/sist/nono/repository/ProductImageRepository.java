
package com.sist.nono.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage	, Integer> {
	
	//상품대표이미지 찾는 
	@Query(value="select p.pr_no from product p, productimage pi order by pr_no", nativeQuery = true)
	List<ProductImage> findByProductOrderByAsc(int pr_no);
}

