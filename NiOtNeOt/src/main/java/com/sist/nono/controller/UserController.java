package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;

@Controller
public class UserController {
	//인증이 안된 사용자들이 출입가능한 경로 ===> /auth/** , / , /js/** , /css/** , /image/**
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/auth/joinForm")
	public String JoinForm() {
		return "user/joinForm";
	}
	
	
	
}
