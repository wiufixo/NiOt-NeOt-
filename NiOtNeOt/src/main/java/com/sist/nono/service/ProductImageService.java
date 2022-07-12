package com.sist.nono.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.ProductImage;
import com.sist.nono.repository.ProductImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductImageService {
	@Autowired
	ProductImageRepository repository;
	
	public List<ProductImage> findAllByProdut(int pr_no){
		return findAllByProdut(pr_no);
	}
	
	//등록과 수정을 위한 메소드
	public void save(ProductImage pi) {
		repository.save(pi);
	}
	
	public ProductImage getPi(int pi_no) {
		return repository.getOne(pi_no);
	}
	
	public void delete(int pi_no) {
		repository.deleteById(pi_no);
	}
	
}
