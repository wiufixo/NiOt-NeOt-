package com.sist.nono.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sist.nono.model.Customer;
import com.sist.nono.service.CustomerService;

import lombok.Setter;

@Controller
@Setter
public class CustomerController {

	@Autowired
	private CustomerService service;

	@GetMapping("customer/join")
	public String join(Model model) {
		model.addAttribute("list",service.findAll());
		return "customer/joinForm.html";
	}
	
	@GetMapping("customer/login")
	public String userlogin() {
		return "customer/login.html";
	}
	
	@GetMapping("customer/findPwd")
	public String findPwd() {	
		return "customer/findPwd.html";
	}
	
	@GetMapping("customer/findPwdOK")
	public String findPwdOK() {
		return "customer/findPwd.html";
	}
}
