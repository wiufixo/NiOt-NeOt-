package com.sist.nono.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		model.addAttribute("list", service.findAll());
		return "customer/joinForm";
	}

	@GetMapping("customer/findPwd")
	public String findPwd() {
		return "customer/findPwd";
	}

	@GetMapping("customer/deleteCheck")
	public String delete(Authentication auth, Model model) {
		Customer cu=service.findByCu_id(auth.getName());
		int cu_no=cu.getCu_no();

		model.addAttribute("cu_no", cu_no);
		model.addAttribute("cu_email", service.findById(cu_no).getCu_email());
		return "customer/deleteCheck";
	}

	@GetMapping("customer/changeImg")
	public String changeImg() {
		return "customer/changeImg";
	}
	
	@GetMapping("customer/login")
	public String customerLogin() {
		return "customer/loginForm";
	}
}
