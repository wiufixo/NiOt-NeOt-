package com.sist.nono.controller.api;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.Customer;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.FollowService;

import lombok.Setter;

@RestController
@Setter
public class CustomerAPIController {

	@Autowired
	CustomerService service;
	
	@PostMapping("customer/findByCu_id")
	public Customer findByCu_id(@RequestParam("cu_id") String cu_id) {
		return service.findByCu_id(cu_id);
	}

	@PostMapping("customer/idCheck")
	public int idCheck(@RequestParam("cu_id") String cu_id) {
		int cu_no=0;
		Customer cu=service.findByCu_id(cu_id);
		cu_no=cu.getCu_no();
		return cu_no;
	}
	
	@PostMapping("customer/emailCheck")
	public int emailCheck(@RequestParam("cu_email") String cu_email) {
		int cu_no=0;
		cu_no=service.findByCu_email(cu_email).getCu_no();
		return cu_no;
	}
	
	@PostMapping("customer/nicknameCheck")
	public int nicknameCheck(@RequestParam("cu_nickname") String cu_nickname) {
		int cu_no=0;
		cu_no=service.findByCu_nickCustomer(cu_nickname).getCu_no();
		return cu_no;
	}

	//이미지 변경
	@Transactional
	@Modifying
	@PostMapping("customer/changeImgProcess")
	public void changeImgProcess(@RequestParam("uploadFile") MultipartFile uploadFile,Authentication auth) {
		Customer cu=service.findByCu_id(auth.getName());
		int cu_no=cu.getCu_no();

		String uploadFolder="C:\\Users\\sonm4\\git\\NiOt-NeOt-\\NiOtNeOt\\src\\main\\resources\\static\\image";
		String uploadFileName=uploadFile.getOriginalFilename();
		uploadFileName=uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
		
		String originalFileName;
		originalFileName=cu.getCu_img();
		File originFile=new File(uploadFolder,originalFileName);
		originFile.delete();
		
		if(uploadFileName!="defaultUserImg") {
			File saveFile=new File(uploadFolder,uploadFileName);
			try {
				uploadFile.transferTo(saveFile);
			}catch(Exception e) {
				System.out.println("에러:"+e.getMessage());
			}
			System.out.println(uploadFileName);
		}
		service.updateCu_img(uploadFileName, cu_no);	
	}

}
