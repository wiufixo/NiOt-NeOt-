package com.sist.nono.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.model.Customer;
import com.sist.nono.service.CustomerService;

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

}
