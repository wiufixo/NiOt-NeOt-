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
	
	@GetMapping("customer/test")
	public Customer test() {
		return service.findByCu_id("tiger");
	}
	
}
