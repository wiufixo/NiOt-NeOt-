package com.sist.nono.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/images/{pi_no}/delete") 
	public void deleteImage(@PathVariable int pi_no){
		this.productService.deleteImage(pi_no);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/{pr_no}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadAjaxPost(MultipartFile[] uploadFile, @PathVariable int pr_no) throws IllegalStateException, IOException {
		if(uploadFile.length < 1) throw new CustomException(ErrorCode.BAD_REQUEST);
		
		int cu_no = 1;
		
		for(int i = 0; i < uploadFile.length; i++) {
			this.productService.saveFiles(uploadFile[i], pr_no, false);	
		}
		
    } // end uploadAjaxPost

}
