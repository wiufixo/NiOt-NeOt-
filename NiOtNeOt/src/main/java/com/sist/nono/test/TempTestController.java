package com.sist.nono.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempTestController {
	
//	템플릿 등 정적 리소스만 사용시 / jsp 관련 설정 모두 삭제해야 동작 
//	@GetMapping("/home") // http/localhost:8080/nono/home ===> templates/home.html 파일을 찾아서 리턴한다
//	public void tempHome() {
//	}
	
//	@GetMapping("/home") // http/localhost:8080/nono/home
//	public String tempHome() {
//		return "/home.html"; /// ===> templates/home.html 파일을 찾아서 리턴한다
//	}
	
	
//	yml파일의 jsp관련 설정 후에 적용됨
	@GetMapping("/temp") // http/localhost:8080/nono/temp
	public String temp() {
		return "temp"; /// ===> WEB-INF/views/temp.jsp 파일을 찾아서 리턴한다
	}
}
