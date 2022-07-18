package com.sist.nono.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Category;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductDeal;
import com.sist.nono.model.ProductImage;

import lombok.RequiredArgsConstructor;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query(value = "select * from product where cu_no=?1", nativeQuery = true)
	List<Product> findAllBycu_no(int cu_no);
	
	@Query(value = "delete from product where cu_id=?1 and pr_no=?2", nativeQuery = true)
	void deleteBycu_noAndpr_no(String cu_id, int pr_no);
	
	
	
	
//	@Query(value="select * from product where pr_no=?1", nativeQuery = true)
//	public Product findOne(int pr_no);
	
//	@Modifying
//	@Query(value="update product set ca_no=?1, image=?2, pr_name=?3,"
//			+ " pr_cost=?4, pr_content=?5, pr_updated=now(),"
//			+ " pr_stauts=?7,pr_deal=?8 where pr_no=?9", nativeQuery = true)
//	public int update(Category ca_no, ProductImage image, String pr_name, int pr_cost,
//			String pr_content, String pr_status, String pr_deal, int pr_no);
//	
	
//	 @Query(value="select * from product order by pr_no desc")
//	 public List<Product> selectAll();
	
//	 @Modifying
//	 @Query(value="insert into product(pr_no, cu_no, ca_no, pi_no, pr_name, pr_cost, pr_content,"
//	 		+ "pr_created, pr_status, pr_deal values(?1,?2,?3,?4.?5.?6,?7,now(),?8,?9 )", nativeQuery = true)
//	  void insertProduct(int pr_no, int cu_no, int ca_no, int pi_no, String pr_name, int pr_cost, String pr_content,
//			 String pr_status, String pr_deal);


	
}