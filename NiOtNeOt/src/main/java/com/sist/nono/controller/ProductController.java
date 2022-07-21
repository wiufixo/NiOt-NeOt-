package com.sist.nono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.model.Product;
import com.sist.nono.service.CategoryService;
import com.sist.nono.service.ProductImageService;
import com.sist.nono.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	
	@Autowired
	private ProductService p;
	
	@Autowired
	private ProductImageService ps;
	
	@Autowired
	private CategoryService cs;
	
	@GetMapping("/list")
	public String productList(Model model){
		List<Product> products = p.findProduct();
		model.addAttribute("products", products);
		return "product/list";
	}

	//게시판 등록화면
	@GetMapping("/insert")
	public String insertForm(Model model) {
		return "product/insert";
	}
	
	//게시판 등록
	@PostMapping("/insert")
	public String insertProduct(@ModelAttribute("product") Product product, Model model) {
		p.saveProduct(product);
		return "product/list";
	}
	
	@GetMapping("/{pr_no}/update")
	public String updateForm(@PathVariable("pr_no") int pr_no,Model model) {
		p.findById(pr_no);
		model.addAttribute("product", p.findById(pr_no));
		return "product/update";
	}
}
