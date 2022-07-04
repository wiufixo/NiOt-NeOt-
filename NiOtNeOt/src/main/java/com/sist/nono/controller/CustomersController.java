package com.sist.nono.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.model.Address;
import com.sist.nono.model.Customer;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.FollowService;
import com.sist.nono.service.LoginListService;
import com.sist.nono.service.TransHistoryService;
import com.sist.nono.service.WishService;

import lombok.Setter;

@Controller
@Setter
public class CustomersController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	FollowService followService;
	
	@Autowired
	LoginListService loginListService;
	
	@Autowired
	TransHistoryService transHistoryService;
	
	@Autowired
	WishService wishService;

	@PostMapping("customer/joinOK")
	@Transactional
	public String joinOK(String cu_id,String cu_pwd,String cu_email,String cu_name,
			String cu_nickname,int cu_gender,int cu_height,int cu_weight,String cu_birth,int privacy_agree,String address,String address_detail,int postcode) {
		
		Customer c=new Customer();
		Address a=new Address();

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
		
		int cu_no=c.getCu_no();
		
		a.setCu_no(cu_no);
		a.setMain_adr_no(postcode);
		a.setMain_adr(address);
		a.setMain_adr_detail(address_detail);
		
		addressService.saveAddress(a);
		return "redirect:/customer/join";
	}
	
	@GetMapping("customer/update")
	public String updateCu(HttpSession session,Model model) {
	//	int cu_no = (Integer)session.getAttribute("cu_no");
		int cu_no=1;
		model.addAttribute("customer",customerService.findById(cu_no));
		model.addAttribute("address",addressService.findById(cu_no));
		
		return "customer/updateForm.html";
	}
	
	@GetMapping("customer/mypage")
	public String mypage(HttpSession session, Model model) {
	//	int cu_no=(Integer)session.getAttribute("cu_no");
		int cu_no=1;
		model.addAttribute("customer",customerService.findById(cu_no));
		model.addAttribute("follower",followService.countFollower(cu_no));
		model.addAttribute("follow",followService.countFollow(cu_no));
		model.addAttribute("transHistory",transHistoryService.countTransHistory(cu_no));
		
		
		return "customer/mypage.html";
	}
	
	@PostMapping("customer/updateOK")
	@Transactional
	public String updateOK(int cu_no,String cu_pwd,String cu_email,String cu_name,
			String cu_nickname,int cu_gender,int cu_height,int cu_weight,String cu_birth,int privacy_agree,String address,String address_detail,int postcode) {
		Customer c=customerService.findById(cu_no);
		Address a=addressService.findById(cu_no);
		
		c.setCu_pwd(cu_pwd);
		c.setCu_email(cu_email);
		c.setCu_name(cu_name);
		c.setCu_nickname(cu_nickname);
		c.setCu_gender(cu_gender);
		c.setCu_height(cu_height);
		c.setCu_weight(cu_weight);
		c.setCu_birth(cu_birth);
		c.setPrivacy_agree(privacy_agree);
		
		a.setMain_adr_no(postcode);
		a.setMain_adr(address);
		a.setMain_adr_detail(address_detail);
		
		customerService.saveCustomer(c);
		addressService.saveAddress(a);
		
		return "redirect:/customer/join";
	}
}
