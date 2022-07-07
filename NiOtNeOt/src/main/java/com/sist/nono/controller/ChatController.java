package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sist.nono.service.ChatService;
import com.sist.nono.service.UserService;

@Controller
public class ChatController {
	@Autowired
	private ChatService service;
	
	@Autowired
	private UserService service2;
	
	@GetMapping("/listChat")
	public String listChat(Model model) {
		model.addAttribute("chat", service.findAll(1));
		model.addAttribute("user",service2.findById("분홍독수리"));
		System.out.println(model.getAttribute("chat"));
		System.out.println(model.getAttribute("user"));
		return "chat/chatList.html";
	}
	
	@GetMapping("/listChats/{cu_no}")
	public void listChat(@PathVariable int cu_no, Model model) {
		model.addAttribute("chat", service.findAll(cu_no));
	}
	
	@RequestMapping("/insertChat")
	public ModelAndView insertChat() {
		System.out.println("채팅 컨트롤러 접근");
		service.insertChat(2,1,1, "dasd");	
		ModelAndView mav = new ModelAndView("redirect:/listChat");
		return mav;
	}
}
