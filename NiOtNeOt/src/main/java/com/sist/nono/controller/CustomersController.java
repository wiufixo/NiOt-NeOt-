package com.sist.nono.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.model.Customer;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.CustomerService;

@Controller
public class CustomersController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddressService addressService;

	@PostMapping("/customer/joinOK")
	@Transactional
	public String joinOK(String cu_id,String cu_pwd,String cu_email,String cu_name,
			String cu_nickname,int cu_gender,int cu_height,int cu_weight,Date cu_birth,int privacy_agree) {
		
		Customer c=new Customer();
		c.setCu_id(cu_id);
		c.setCu_pwd(cu_pwd);
		c.setCu_email(cu_email);
		c.setCu_name(cu_name);
		c.setCu_nickname(cu_nickname);
		c.setCu_gender(cu_gender);
		c.setCu_height(cu_height);
		c.setCu_weight(cu_weight);
		c.setCu_birth(cu_birth);
		c.setPrivacy_agree(privacy_agree);
		
		customerService.saveCustomer(c);
		
		return "/";
	}
	
}
