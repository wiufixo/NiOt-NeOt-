package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.nono.service.ChatRoomService;
import com.sist.nono.service.CustomerService;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService service;

	@Autowired		
	private CustomerService service2;
	
	@GetMapping("/listChatRoom")
	public String listchatRoom(Model model, Authentication auth) {
		model.addAttribute("roomlist", service.findAll(service2.findByCu_id(auth.getName()).getCu_no()));
		System.out.println(service.findAll(service2.findByCu_id(auth.getName()).getCu_no()));
		return "chat/chatRoomList.html";
	}
	
	@GetMapping("/listChatRooms/{cu_no}")
	public void listChatRoom(@PathVariable int cu_no, Model model) {
		model.addAttribute("chatroomlist", service.findAll(cu_no));
	}

	@RequestMapping("createChatRoom")
	public void createChatRoom(Authentication auth, int bcu_no, int pr_no) {
		service.create(service2.findByCu_id(auth.getName()).getCu_no(), bcu_no, pr_no);
	}
}
