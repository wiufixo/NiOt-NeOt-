package com.sist.nono.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.dto.ProductDTO;
import com.sist.nono.model.Category;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductDeal;
import com.sist.nono.model.ProductImage;
import com.sist.nono.repository.CategoryRepository;
import com.sist.nono.repository.CustomerRepository;
import com.sist.nono.repository.ProductImageRepository;
import com.sist.nono.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
	
	@Value("${file.dir}")
	private String fileDir;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductImageRepository prouctImageRepository;
	
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	@Transactional
	public Product saveProduct(ProductDTO p) {
		
		Category category = categoryRepository.findByca_name(p.getCa_name());
		Product product = new Product(p.getPr_no(), category
				, null, p.getPr_name(), p.getPr_cost(), p.getCu_no(), p.getPr_content()
				, ProductDeal.valueOf(p.getPr_deal()), null, null);
		return productRepository.save(product);
	}
	
	@Transactional
	public void updateProduct(ProductDTO p) {
		
		Category category = categoryRepository.findByca_name(p.getCa_name());
		Product existProduct = productRepository.findById(p.getPr_no()).get();
		existProduct.updateProduct(p, category);
		
		productRepository.save(existProduct);
	}
	
	
	public Product findProduct(int pr_no) {
		return this.productRepository.findById(pr_no).get();
	}
	
	public List<ProductImage> findProductImage(int pr_no) {
		return this.prouctImageRepository.findByPrNo(pr_no);	
	}
	
	public List<Product> findProductByCategory(int ca_no) {
		return this.productRepository.findAll()
		.stream()
		.filter(p -> p.getCategory().getCa_no() == ca_no)
		.collect(Collectors.toList());
	}
	
	public void saveFiles(MultipartFile file, int pr_no, Boolean repImgYn) throws IllegalStateException, IOException {
		if(file.isEmpty()) return;
		
		String originalName = file.getOriginalFilename();
		
		String extension = originalName.substring(originalName.lastIndexOf("."));
		
		String savePath = fileDir + "/image/product/" + originalName;
		file.transferTo(new File(savePath));
		
		String resourcePath = "/image/product/" + originalName;
		Product p = this.productRepository.findById(pr_no).get();
		ProductImage image = new ProductImage(-1, p, originalName, null, null, resourcePath, repImgYn);
		this.prouctImageRepository.save(image);
	}
	
	public Boolean hasRepresentImage(int pr_no) {
		Optional<ProductImage> p = this.prouctImageRepository.findByPrNoAndRepImgYn(pr_no);
		if(p.isPresent())
			return true;
		else
			return false;
	}
	
	public void delete(int pr_no, String loginId) {
		this.productRepository.deleteBycu_noAndpr_no(loginId, pr_no);
		this.prouctImageRepository.deleteBypr_no(pr_no);
	}
	
	public List<Product> findAllByCu_no(int cu_no) {
		return this.productRepository.findAllBycu_no(cu_no);
	}
	
	public Product findById(int pr_no) {
		return this.productRepository.findById(pr_no).get();
	}
	
	public void deleteImage(int pi_no) {
		this.prouctImageRepository.deleteById(pi_no);
	}
}
