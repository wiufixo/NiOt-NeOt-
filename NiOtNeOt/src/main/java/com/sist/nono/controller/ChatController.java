package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nono.service.ChatService;

@Controller
public class ChatController {
	@Autowired
	private ChatService service;	
	
	@GetMapping("/chat")
	public String chat() {
		return "chat/chatList";
	}
	
	@GetMapping("/listChat/{cu_no}")
	public void listChat(@PathVariable int cu_no, Model model) {
		model.addAttribute("chatlist", service.findAll(cu_no));
	}
	
	@GetMapping("/chat/{cu_no}")
	public String findById(@PathVariable int cu_no, Model model) {
		model.addAttribute("alert",service.findAll(cu_no));
		return "alert/detail";
	}
}
