package com.sist.nono.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.nono.config.CustomerValidator;
import com.sist.nono.config.joinFormValidator;
import com.sist.nono.model.Address;
import com.sist.nono.model.Board;
import com.sist.nono.model.Customer;
import com.sist.nono.model.Follow;
import com.sist.nono.model.JoinForm;
import com.sist.nono.model.Product;
import com.sist.nono.model.TransHistory;
import com.sist.nono.model.Wish;
import com.sist.nono.service.AddressService;
import com.sist.nono.service.BoardService;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.FeedService;
import com.sist.nono.service.FollowService;
import com.sist.nono.service.LoginListService;
import com.sist.nono.service.ProductImageService;
import com.sist.nono.service.ProductService;
import com.sist.nono.service.TransHistoryService;
import com.sist.nono.service.WishService;

import lombok.Setter;

@Controller
@Setter
public class CustomersController {
	
	
	private static final Logger log = LoggerFactory.getLogger(CustomersController.class);

	
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
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	FeedService feedService;
	
	@Autowired JavaMailSender javaMailSender;
	
	@PostMapping("customer/joinOK")
	public String joinOK(@Valid JoinForm joinForm,BindingResult bindingResult,HttpServletRequest request) {
		//model에서 설정한 유효성
		if(bindingResult.hasErrors()) {
			return "/customer/join";
		}
		//validator에서 설정한 유효성
		joinFormValidator validator = new joinFormValidator(customerService);
		validator.validate(joinForm, bindingResult);
		if(bindingResult.hasErrors()) {
			return "/customer/join";
		}
		//joinForm에서 customer로 옮기기
		Customer customer = new Customer();
		customer.setAddress(joinForm.getAddress());
		customer.setAddress_detail(joinForm.getAddress_detail());
		customer.setCu_birth(joinForm.getCu_birth());
		customer.setCu_email(joinForm.getCu_email());
		customer.setCu_gender(joinForm.getCu_gender());
		customer.setCu_height(joinForm.getCu_height());
		customer.setCu_id(joinForm.getCu_id());
		customer.setCu_name(joinForm.getCu_name());
		customer.setCu_nickname(joinForm.getCu_nickname());
		customer.setCu_pwd(joinForm.getCu_pwd());
		customer.setCu_weight(joinForm.getCu_weight());
		customer.setPostcode(joinForm.getPostcode());
		customer.setPrivacy_agree(joinForm.getPrivacy_agree());
		
		customer.setCu_img("defaultUserImg.jpg");
		customerService.saveCustomer(customer);
		
		return "/index";
	}
	
	@PostMapping("customer/updateOK")
	public String updateOK(@Valid Customer customer,BindingResult bindingResult,HttpServletRequest request,Model model) {
		if(bindingResult.hasErrors()) {
			return "customer/update";
		}
		//validator에서 설정한 유효성
		CustomerValidator validator = new CustomerValidator(customerService);
		validator.validate(customer, bindingResult);
		if(bindingResult.hasErrors()) {
			return "customer/update";
		}
		
		customerService.saveCustomer(customer);
		
		return "/index";
	}
	
	@GetMapping("customer/update")
	public String updateCu(Authentication auth,Model model) {
		Customer cu=customerService.findByCu_id(auth.getName());
		int cu_no=cu.getCu_no();
		model.addAttribute("customer",customerService.findById(cu_no));
		
		return "customer/update";
	}
	
	
	//user_no에 보려는 유저의 cu_no 입력
	@GetMapping("customer/pageChoice")
	public String pageChoice(Authentication auth, Model model,@RequestParam("user_no") int user_no) {
		int cu_no=0;

		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("followerNum",followService.countFollower(user_no));
		model.addAttribute("followingNum",followService.countFollow(user_no));
		model.addAttribute("transNum",transHistoryService.countTransHistory(user_no));
		model.addAttribute("wishNum",wishService.countWish(user_no));
		model.addAttribute("board",boardService.findAllByCu_no(user_no));
		model.addAttribute("product",productService.findAllByCu_no(user_no));
		model.addAttribute("feed",feedService.findByCu_no(user_no));
		
		//로그인하지 않고 유저페이지로 들어갈 때
		if(auth==null) {
			model.addAttribute("customer",customerService.findById(cu_no));
			model.addAttribute("checkFollow",followService.checkFollow(cu_no, user_no));
			
			return "customer/userpage";
		}
		
		cu_no=customerService.findByCu_id(auth.getName()).getCu_no();
		
		//마이페이지로 들어갈 때
		if(user_no==cu_no) {
			model.addAttribute("customer",customerService.findById(cu_no));
			
			return "customer/mypage";
		
		//다른 유저의 유저페이지로 들어갈 때
		}else if(user_no!=cu_no) {
			model.addAttribute("customer",customerService.findById(cu_no));
			model.addAttribute("checkFollow",followService.checkFollow(cu_no, user_no));
			
			return "customer/userpage";
		}
		return "OK";
	}
	
	@PostMapping("customer/deleteCustomer")
	@Transactional
	public void deleteCustomer(@RequestParam("cu_no") int cu_no) {
		customerService.deleteCustomer(cu_no);
	}

	@GetMapping("customer/boardExpantion/{user_no}")
	public String boardExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("board",boardService.findAllByCu_no(user_no));
		return "customer/boardExpantion";
	}

	@GetMapping("customer/productExpantion/{user_no}")
	public String productExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("product",productService.findAllByCu_no(user_no));
		return "customer/productExpantion";
	}
	
	@GetMapping("customer/feedExpantion/{user_no}")
	public String feedExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("feed",feedService.findByCu_no(user_no));
		return "customer/feedExpantion";
	}
	
	@GetMapping("customer/transExpantion/{user_no}")
	public String transExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		List<TransHistory> list1= transHistoryService.findAllByCu_no(user_no);
		ArrayList<Product> list=new ArrayList<Product>();
		
		for(TransHistory trans :list1) {
			list.add(productService.findProduct((trans.getPr_no()))); 
		}
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("trans",list);
		return "customer/transExpantion";
	}
	
	@GetMapping("customer/wishExpantion/{user_no}")
	public String wishExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		List<Wish> list1= wishService.findAllByCu_no(user_no);
		ArrayList<Product> list=new ArrayList<Product>();
		
		for(Wish wish :list1) {
			list.add(productService.findProduct((wish.getPr_no()))); 
		}
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("wish",list);
		return "customer/wishExpantion";
	}
	
	@GetMapping("customer/followerExpantion/{user_no}")
	public String followerExpantion(Authentication auth,Model model,@PathVariable int user_no) {
		List<Follow> list1= followService.findByFollowed(user_no);
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		for(Follow follow :list1) {
			list.add(customerService.findById((follow.getFollower()))); 
		}
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("follower",list);
		
		return "customer/followerExpantion";
	}
	
	@GetMapping("customer/followingExpantion/{user_no}")
	public String folllowingExpantion(Authentication auth,Model model,@PathVariable int user_no){
		List<Follow> list1= followService.findByFollower(user_no);
		ArrayList<Customer> list=new ArrayList<Customer>();
		
		for(Follow follow :list1) {
			list.add(customerService.findById((follow.getFollowed()))); 
		}
		model.addAttribute("user",customerService.findById(user_no));
		model.addAttribute("following",list);
		
		return "customer/followingExpantion";
	}
}
