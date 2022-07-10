package com.sist.nono.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Product;
import com.sist.nono.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findProduct(){
		return repository.findAll();
	}
	//주 식별자가 있으면 update 없으면 insert
	@Transactional
	public void saveProduct(Product product) {
		repository.save(product);
	}
	
	
	public void deleteProduct(int pr_no) {
		repository.deleteById(pr_no);
	}
	
	public Object findById(int pr_no) {
		return repository.findById(pr_no);
	}
	
}
