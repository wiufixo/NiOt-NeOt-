package com.sist.nono.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.dto.ResponseDTO;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;
import com.sist.nono.service.UserService;

@RestController
public class UserAPIController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> join(@RequestBody User user) {
		System.out.println("UserAPIController:join호출");
		int re = service.join(user); // 여기서 no,regdate 자동입력되어 입력된다 
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),re);
	} 
	
	
	
	
	
//	전통로그인방식
//	@PostMapping("/api/user/login")
//	public ResponseDTO<Integer> login(@RequestBody User user, HttpSession session){
//		System.out.println("UserAPIController:login호출");
//		System.out.println("user:"+user);
//		User principal = service.login(user); // 로그인 접근주체 객체
//		System.out.println("principal:"+principal);
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
//	}
}
