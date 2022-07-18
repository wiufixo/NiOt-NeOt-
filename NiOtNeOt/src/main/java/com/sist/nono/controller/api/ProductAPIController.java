package com.sist.nono.controller.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.exception.CustomException;
import com.sist.nono.exception.ErrorCode;
import com.sist.nono.service.ProductService;

@RequestMapping("/api/products")
@RestController
public class ProductAPIController {

	@Autowired
	ProductService productService;
	
	
	@PostMapping("")
	public void createImage() {
		
	}
	
	@DeleteMapping("/{pi_no}")
	public void deleteImage(@PathVariable int pi_no) {
		
	}
	
	@PatchMapping("/{pr_no}/files")
	public void uploadFiles(@PathVariable int pr_no, @RequestParam("files") List<MultipartFile> files) throws IllegalStateException, IOException {
		if(files.size() < 1) throw new CustomException(ErrorCode.BAD_REQUEST);
		
		for(int i = 0; i < files.size(); i++) {
			if(i == 0)
				productService.saveFiles(files.get(i), pr_no, true);
			else 
				productService.saveFiles(files.get(i), pr_no, false);
		}
	
		
	}
}
