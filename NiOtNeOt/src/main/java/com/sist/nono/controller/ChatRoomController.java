package com.sist.nono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nono.model.Chat;
import com.sist.nono.model.ChatRoom;
import com.sist.nono.service.ChatRoomService;
import com.sist.nono.service.ChatService;
import com.sist.nono.service.CustomerService;
import com.sist.nono.service.ProductService;

@Controller
public class ChatRoomController {
	@Autowired
	private ChatRoomService service;

	@Autowired		
	private CustomerService service2;
	
	@Autowired
	private ChatService service3;

	@Autowired
	private ProductService service4;
	
	@GetMapping("/listChatRoom")
	public String listchatRoom(Model model, Authentication auth) {
		model.addAttribute("roomlist", service.findAll(service2.findByCu_id(auth.getName()).getCu_no()));
		System.out.println(service.findAll(service2.findByCu_id(auth.getName()).getCu_no()));
		return "chat/chatRoomList.html";
	}
	
	@GetMapping("/testproduct")
	public String testproduct() {
		return "chat/testproduct.html";
	}
	
//	@GetMapping("/listChatRoom")
//	public String listchatRoom(Model model) {
//		model.addAttribute("roomlist", service.findAll(1));
//		System.out.println(service.findAll(1));
//		return "chat/chatRoomList.html";
//	}
//	
//	@GetMapping("/listChatRoom")
//	public String listchatRoom(Model model) {
//		model.addAttribute("roomlist", service3.findRecentChatList(1));
//		System.out.println(service3.findRecentChatList(1));
//		return "chat/chatRoomList.html";
//	}
	
//	@ResponseBody
//	@GetMapping("/listChatRoom")
//	public void listchatRoom(Model model) {
//		model.addAttribute("roomlist", service.findAll(1));
//		System.out.println(service.findAll(1));
//	}
	
//	@ResponseBody
//	@RequestMapping("/listChatRoom")
//	public List<ChatRoom> listchatRoom(Model model) {
//		List<ChatRoom> list = service.findAll(1);
//		System.out.println(list);
//		return list;
//	}

	@PostMapping("/createChatRoom")
	public String createChatRoom(Authentication auth, int pr_no) {
		int bcu_no = service4.findById(pr_no).getCu_no();
		System.out.println("asdasdasdasdzzzzzzzzzzzzzzzzzzzz" +bcu_no);
		int cu_no = service2.findByCu_id(auth.getName()).getCu_no();
		System.out.println("ssssssssssssss"+cu_no);
		service.create(bcu_no, cu_no, pr_no);
		return "채팅방 생성 완료";
	}
	
	@PostMapping("/updateChatTrade")
	public void updateChatTrade(Authentication auth, int cr_no) {
		System.out.println("cr_no = " + cr_no);
		service.updateRoomTrade(cr_no);
	}
}
