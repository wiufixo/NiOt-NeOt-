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
import com.sist.nono.model.Category;
import com.sist.nono.model.Product;
import com.sist.nono.service.CategoryService;
import com.sist.nono.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/products")
@Controller
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	//리스트
	@GetMapping("")
	public String list(HttpSession httpSession, Model model) {
		List<Product> list =  productService.findAll();
		List<ProductDTO> dtoList = list.stream().map(p -> new ProductDTO(p))
		.collect(Collectors.toList());
		
		List<Category> categories = categoryService.findAll();
		
		model.addAttribute("products",dtoList);
		model.addAttribute("categories",categories);
		return "product/list";
	}
	
	@GetMapping("/{pr_no}")
	public String detail(Model model, @PathVariable int pr_no  ) {
		Product p = productService.findProduct(pr_no);
		model.addAttribute("product", p);
		return "product/detail";
	}
	
	@GetMapping("/categories/{ca_no}")
	public String productByCategory(Model model, @PathVariable int ca_no  ) {
		List<Product> list = productService.findProductByCategory(ca_no);
		List<ProductDTO> dtoList = list.stream().map(p -> new ProductDTO(p))
		.collect(Collectors.toList());
		
		List<Category> categories = categoryService.findAll();
		
		model.addAttribute("products",dtoList);
		model.addAttribute("categories",categories);
		return "product/list";
	}

	@GetMapping("/insert")
	public String createProducts( ) {
		return "/product/insert";
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