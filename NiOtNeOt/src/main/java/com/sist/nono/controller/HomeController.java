package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.User;
import com.sist.nono.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager manager;
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@GetMapping("/account/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/account/join")
	public String join() {
		return "account/join";
	}
	
	@PostMapping("/account/join")
	public String joinSubmit(User user) {
		userService.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/account/update")
	public String update(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		model.addAttribute("principal", principal);
		return "account/update";
	}
	
	@PostMapping("/account/update")
	public String updateSubmit(User user) {
		
		userService.update(user); // 회원정보 수정은 DB에 적용이 되지만 session값은 변경되지 않은 상태기 때문에 직접 세션 변경해줘야한다
		//세션등록
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getCu_id(), user.getCu_pwd()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
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
