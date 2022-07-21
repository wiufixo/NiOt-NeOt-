
package com.sist.nono.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.nono.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage	, Integer> {
	
	//상품대표이미지 찾는 
	@Query(value="select p.pr_no from product p, productimage pi order by pr_no", nativeQuery = true)
	List<ProductImage> findByProductOrderByAsc(int pr_no);
	
	@Query(value="select pi.pr_no, pi.repImgYn from productimage pi where pi.pr_no and repImgYn = 1", nativeQuery = true)
	Optional<ProductImage> findByPrNoAndRepImgYn(int pr_no);

	@Query(value = "select * from productimage pi where pi.pr_no = :pr_no", nativeQuery = true)
	List<ProductImage> findByPrNo(@Param("pr_no")int pr_no);
	
	@Query(value = "delete from productimage where pr_no = :pr_no", nativeQuery = true)
	void deleteBypr_no(int pr_no);
	
}

