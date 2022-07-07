package com.sist.nono.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nono.model.Address;
import com.sist.nono.model.Board;
import com.sist.nono.model.Customer;
import com.sist.nono.model.Follow;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.BoardService;
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
	
	@Autowired
	BoardService boardService;

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
		
		customerService.join(c);
		
		int cu_no=c.getCu_no();
		
		a.setCu_no(cu_no);
		a.setMain_adr_no(postcode);
		a.setMain_adr(address);
		a.setMain_adr_detail(address_detail);
		
		addressService.saveAddress(a);
		return "customer/loginForm";
	}
	
	@GetMapping("customer/update")
	public String updateCu(Authentication auth,Model model) {
		Customer cu=customerService.findByCu_id(auth.getName());
		int cu_no=cu.getCu_no();
		model.addAttribute("customer",customerService.findById(cu_no));
		model.addAttribute("address",addressService.findById(cu_no));
		
		return "customer/updateForm";
	}
	
	@GetMapping("customer/pageChoice")
	public String pageChoice(Authentication auth, Model model,@RequestParam("user_no") int user_no) {
		//cu_no=0은 빈 가짜 유저로 만들 것
		int cu_no=0;
		
		//로그인하지 않고 유저페이지로 들어갈 때
		if(auth==null) {
			model.addAttribute("customer",customerService.findById(cu_no));
			model.addAttribute("user",customerService.findById(user_no));
			model.addAttribute("checkFollow",followService.checkFollow(cu_no, user_no));
			model.addAttribute("followerNum",followService.countFollower(user_no));
			model.addAttribute("followingNum",followService.countFollow(user_no));
			model.addAttribute("transNum",transHistoryService.countTransHistory(user_no));
			model.addAttribute("wishNum",wishService.countWish(user_no));
			model.addAttribute("board",boardService.findAllByCu_no(user_no));
			
			return "customer/userpage";
		}
		
		cu_no=customerService.findByCu_id(auth.getName()).getCu_no();
		
		//유저가 자신의 유저페이지로 들어갈 때
		if(user_no==cu_no) {
			model.addAttribute("customer",customerService.findById(cu_no));
			model.addAttribute("followerNum",followService.countFollower(cu_no));
			model.addAttribute("followingNum",followService.countFollow(cu_no));
			model.addAttribute("transNum",transHistoryService.countTransHistory(cu_no));
			model.addAttribute("wishNum",wishService.countWish(cu_no));
			model.addAttribute("board",boardService.findAllByCu_no(cu_no));
			return "customer/mypage";
		
		//다른 유저의 유저페이지로 들어갈 때
		}else if(user_no!=cu_no) {
			
			List<Board> board = boardService.findAllByCu_no(user_no);
			model.addAttribute("customer",customerService.findById(cu_no));
			model.addAttribute("user",customerService.findById(user_no));
			model.addAttribute("checkFollow",followService.checkFollow(cu_no, user_no));
			model.addAttribute("followerNum",followService.countFollower(user_no));
			model.addAttribute("followingNum",followService.countFollow(user_no));
			model.addAttribute("transNum",transHistoryService.countTransHistory(user_no));
			model.addAttribute("wishNum",wishService.countWish(user_no));
			model.addAttribute("board",board);
			return "customer/userpage";
		}
		
		return "OK";
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
		
		return "/index";
	}
	
	@PostMapping("customer/deleteCustomer")
	@Transactional
	public void deleteCustomer(@RequestParam("cu_no") int cu_no) {
		addressService.deleteAddress(cu_no);
		customerService.deleteCustomer(cu_no);
	}

	@GetMapping("customer/boardExpantion")
	public String boardExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		return "customer/boardExpantion";
	}

	@GetMapping("customer/productExpantion")
	public String productExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		
		return "customer/productExpantion";
	}
	
	@GetMapping("customer/feedExpantion")
	public String feedExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		
		return "customer/feedExpantion";
	}
	
	@GetMapping("customer/transExpantion")
	public String transExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		
		return "customer/transExpantion";
	}
	
	@GetMapping("customer/wishExpantion")
	public String wishExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		
		return "customer/wishExpantion";
	}
	
	@GetMapping("customer/followerExpantion")
	public String followerExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no) {
		
		//user=접속자
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		List<Follow> list1= followService.findByFollowed(user_no);
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		for(Follow follow :list1) {
			list.add(customerService.findById((follow.getFollower()))); 
		}
		model.addAttribute("customer",customerService.findById(user_no));
		model.addAttribute("follower",list);
		
		return "customer/followerExpantion";
	}
	
	@GetMapping("customer/followingExpantion")
	public String folllowingExpantion(Authentication auth,Model model,@RequestParam("user_no") int user_no){
		if(user_no==0) {
			user_no=customerService.findByCu_id(auth.getName()).getCu_no();
		}
		
		List<Follow> list1= followService.findByFollower(user_no);
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		for(Follow follow :list1) {
			list.add(customerService.findById((follow.getFollowed()))); 
		}
		model.addAttribute("customer",customerService.findById(user_no));
		model.addAttribute("following",list);
		
		return "customer/followingExpantion";
	}
}
