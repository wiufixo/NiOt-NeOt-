package com.sist.nono.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private AuthenticationManager manager;
	
	@GetMapping("/account/board")
	public String index() {
		return "account/board";
	}
	
//	@GetMapping("/account/login")
//	public String login() {
//		return "account/login";
//	}
//
//	@GetMapping("/account/join")
//	public String join(Model model) {
//		model.addAttribute("user", new User());
//		return "account/join";
//	}
//	
//	@PostMapping("/account/join")
//	public String joinSubmit(@Valid User user, BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			log.info(bindingResult.getAllErrors().get(0).getDefaultMessage());
//			return "/account/join";
//		}
//		userService.save(user);
//		return "redirect:/";
//	}
//	
//	@GetMapping("/account/update")
//	public String update(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
//		model.addAttribute("user", principal.getCustomer());
//		return "account/update";
//	}
//	
//	@PostMapping("/account/update")
//	public String updateSubmit(@Valid User user, BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			log.info(bindingResult.getAllErrors().get(0).getDefaultMessage());
//			return "/account/update";
//		}
//		userService.update(user); // 회원정보 수정은 DB에 적용이 되지만 session값은 변경되지 않은 상태기 때문에 직접 세션 변경해줘야한다
//		//세션등록
//		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getCu_id(), user.getCu_pwd()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		return "redirect:/";
//	}
	
//	//회원탈퇴
//	@GetMapping("/resign")
//	public String resign(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
//		model.addAttribute("principal", principal);
//		System.out.println(principal.getPassword());
//		System.out.println(principal.getUsername());
//		System.out.println(principal.getUser().getCu_id());
//		System.out.println(principal.getUser().getCu_pwd());
//		return "account/resign";
//	}
//	//회원탈퇴
//	@PostMapping("/user/delete")
//	public String delete(User user) {
//		userService.resign(user);
//		return "redirect:/";
//	}
}
