package com.sist.nono.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.dto.ProductFormDTO;
import com.sist.nono.dto.ProductImageDTO;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductDeal;
import com.sist.nono.model.ProductImage;
import com.sist.nono.model.ProductStatus;
import com.sist.nono.repository.ProductImageRepository;
import com.sist.nono.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	
	@Transactional
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
	
	
	public Product findProduct(int pr_no) {
		return this.productRepository.findById(pr_no).get();
		//return productRepository.findOne(pr_no);
	}
}
