package com.sist.nono.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sist.nono.dto.KakaoProfileDTO;
import com.sist.nono.dto.OAuthTokenDTO;
import com.sist.nono.dto.ResponseDTO;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;
import com.sist.nono.service.UserService;

@Controller
public class UserController {
	
	@Value("${sist.key}")
	private String sistKey;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private AuthenticationManager manager;
	
	//인증이 안된 사용자들이 출입가능한 경로 ===> /auth/** , / , /js/** , /css/** , /image/**
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "/WEB-INF/views/user/loginForm.jsp";
	}
	
	@GetMapping("/auth/joinForm")
	public String JoinForm() {
		return "/WEB-INF/views/user/joinForm.jsp";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaologin(String code) {
		//post 방식으로 key=value 형식의 데이터를 요청할 수 있는 객체
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 객체
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 객체
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "00e34125548339316c0af9949d7e464a");
		params.add("redirect_ur", "http://localhost:8080/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
		
		// Http post 방식으로 response 변수의 응답을 받는다
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, 
				kakaoTokenRequest, 
				String.class
				);
		
		// json을 객체형식으로 변환해주는 mapper (gson, json simple 등도 있다)
		ObjectMapper objMapper = new ObjectMapper();
		OAuthTokenDTO token = null;
		try {
			token = objMapper.readValue(response.getBody(), OAuthTokenDTO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("token : "+token.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+token.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", 
				HttpMethod.POST, 
				kakaoProfileRequest, 
				String.class
				);
		
		ObjectMapper objMapper2 = new ObjectMapper();
		KakaoProfileDTO profile = null;
		try {
			profile = objMapper2.readValue(response2.getBody(), KakaoProfileDTO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("아이디: "+profile.getKakao_account().getEmail()+"_"+profile.getId());
		System.out.println("이메일: "+profile.getKakao_account().getEmail());
		System.out.println("패스워드: "+sistKey);
		System.out.println("권한: "+"USER");
		
		User user = User.builder()
				.cu_id(profile.getKakao_account().getEmail()+"_"+profile.getId())
				.cu_pwd(sistKey)
				.cu_email(profile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		User originUser = service.findById(user.getCu_id());
		
		// 기존회원이 아니면 join
		if(originUser.getCu_id() == null) {
			service.join(user);
			
		}
		// 자동로그인처리
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getCu_id(), sistKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
		
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "/WEB-INF/views/user/updateForm.jsp";
	}
	
	
}
