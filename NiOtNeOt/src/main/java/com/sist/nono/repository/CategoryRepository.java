package com.sist.nono.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//Category findByca_name(String ca_name);
	
	@Query(value="select * from category where ca_name=?1", nativeQuery = true)
	Category findByca_name(String ca_name);
}
