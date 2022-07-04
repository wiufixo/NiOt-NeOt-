package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nono.service.ChatRoomService;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService service;
	
	@GetMapping("/listGoods/{cu_no}")
	public void listGoods(@PathVariable int cu_no, Model model) {
		model.addAttribute("chatroomlist", service.findAll(cu_no));
	}
}
