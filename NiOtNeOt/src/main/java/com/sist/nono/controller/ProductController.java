package com.sist.nono.controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.nono.dto.ProductDTO;
import com.sist.nono.model.Product;
import com.sist.nono.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/products")
@Controller
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//리스트
	@GetMapping("")
	public String list(HttpSession httpSession, Model model) {
		List<Product> list =  productService.findAll();
		List<ProductDTO> dtoList = list.stream().map(p -> new ProductDTO(p))
		.collect(Collectors.toList());
		
		model.addAttribute("products",dtoList);
		return "product/list";
	}
	
	@GetMapping("/{pr_no}")
	public String detail(Model model, @PathVariable int pr_no  ) {
		Product p = productService.findProduct(pr_no);
		model.addAttribute("product", p);
		return "product/detail";
	}
	
	
	
	//게시글 저장
	@GetMapping(value="/product/new")
	public String insertProduct() {
		return "/product/insert";
	}
	
	@PostMapping("/product/new")
	public String insertProdcut(int pr_no, String pr_name, int pr_cost, int ca_no, String pr_content,
			String pr_deal) {
		
		
		return "/product/insert";
}
	

}