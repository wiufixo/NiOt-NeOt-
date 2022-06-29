package com.sist.nono.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpTestController {
	
	@GetMapping("http/get")
	public String getTest(Member m) {
		return "get요청임!!"+m.getId()+m.getUsername()+m.getPassword();
	}
	@PostMapping("http/post")
	public String postTest(@RequestBody Member m) {
		return "post요청임!!"+m.getId()+m.getUsername()+m.getPassword();
	}
	@PutMapping("http/put")
	public String putTest() {
		return "put요청임!!";
	}
	@DeleteMapping("http/delete")
	public String deleteTest() {
		return "delete요청임!!";
	}
	
}
