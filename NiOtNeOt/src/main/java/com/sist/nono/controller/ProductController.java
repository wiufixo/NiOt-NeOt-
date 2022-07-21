package com.sist.nono.controller;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.dto.ProductDTO;
import com.sist.nono.dto.ProductImageDTO;
import com.sist.nono.exception.CustomException;
import com.sist.nono.exception.ErrorCode;
import com.sist.nono.model.Category;
import com.sist.nono.model.Customer;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductImage;
import com.sist.nono.service.CategoryService;
import com.sist.nono.service.CustomerService;
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
	@Autowired
	private CustomerService customerService;
	
	//리스트
	@GetMapping("")
	public String list(HttpSession httpSession, Model model,Authentication auth) {
		List<Product> list =  productService.findAll();
		List<ProductDTO> dtoList = list.stream().map(p -> new ProductDTO(p))
		.collect(Collectors.toList());
		List<Category> categories = categoryService.findAll();
		
		
		model.addAttribute("address",customerService.findByCu_id(auth.getName()).getAddress_detail());
		model.addAttribute("nicknames",customerService.findByCu_id(auth.getName()).getCu_nickname());
		model.addAttribute("cu_images", customerService.findByCu_id(auth.getName()).getCu_img());
		model.addAttribute("products",dtoList);
		model.addAttribute("categories",categories);
		return "product/list";
	}
	
	
	@GetMapping("/{pr_no}")
	public String detail(Model model, @PathVariable int pr_no, Authentication auth) {
		Product p = productService.findProduct(pr_no);
		List<ProductImage> images = productService.findProductImage(pr_no);

		ProductDTO dto = new ProductDTO(p);
		
		List<ProductImageDTO> imageDto = images.stream()
				.map(i -> new ProductImageDTO(i))
				.collect(Collectors.toList());

		model.addAttribute("nicknames",customerService.findByCu_id(auth.getName()).getCu_nickname());
		model.addAttribute("cu_images", customerService.findByCu_id(auth.getName()).getCu_img());
		model.addAttribute("product", dto);
		model.addAttribute("images", imageDto);
		return "product/detail2";
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
	public String insetView(Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories",categories);
		return "/product/insert";
	}
	@PostMapping("/insert")
	public String insertProdcut( int pr_no, String ca_name, int pr_cost, String pr_image
			, String pr_name, String pr_content, String pr_deal
			, @RequestParam("pr_images") List<MultipartFile> files,Authentication auth) throws IllegalStateException, IOException {
		if(files.size() < 1) throw new CustomException(ErrorCode.BAD_REQUEST);
		
		int cu_no = customerService.findByCu_id(auth.getName()).getCu_no();
		
		ProductDTO p = new ProductDTO(pr_no, ca_name, pr_image
				, pr_name, pr_cost, pr_content, pr_deal, cu_no);
		Product newProduct = this.productService.saveProduct(p);
		
		for(int i = 0; i < files.size(); i++) {
			if(i == 0)
				productService.saveFiles(files.get(i), newProduct.getPr_no(), true);
			else 
				productService.saveFiles(files.get(i), newProduct.getPr_no(), false);
		}
		
		return "redirect:/products";
	}
	
	@GetMapping("/{pr_no}/update")
	public String updateView(Model model, @PathVariable Integer pr_no) {
		Product p = productService.findProduct(pr_no);
		ProductDTO dto = new ProductDTO(p);
		List<ProductImage> images = productService.findProductImage(pr_no);
		List<ProductImageDTO> imageDto = images.stream()
				.map(i -> new ProductImageDTO(i))
				.collect(Collectors.toList());
		List<Category> categories = categoryService.findAll();
		model.addAttribute("product", dto);
		model.addAttribute("categories",categories);
		model.addAttribute("images", imageDto);
		return "product/update";
	}
	@PostMapping("/{pr_no}/update")
	public String upateProdcut(int pr_no, String ca_name, int pr_cost
			, String pr_name, String pr_content, String pr_deal, Authentication auth) {
		
		
		int cu_no = customerService.findByCu_id(auth.getName()).getCu_no();
		
		ProductDTO p = new ProductDTO(pr_no, ca_name, ""
				, pr_name, pr_cost, pr_content, pr_deal, cu_no);
		this.productService.updateProduct(p);
		
		
		return "redirect:/products";
	}
	
	@GetMapping("/{pr_no}/delete")
	public String deleteProduct(@PathVariable int pr_no) {
		String loginId = "one";
		
		this.productService.delete(pr_no, loginId);
		return "redirect:/products";
	}
	
}
	 