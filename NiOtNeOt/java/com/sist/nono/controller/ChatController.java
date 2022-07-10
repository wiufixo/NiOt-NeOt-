package com.sist.nono.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.model.Chat;
import com.sist.nono.service.ChatRoomService;
import com.sist.nono.service.ChatService;
import com.sist.nono.service.CustomerService;

@Controller
public class ChatController {
	@Autowired
	private ChatService service;

	@Autowired
	private ChatRoomService service2;

	@Autowired		// 유저 번호를 상태유지 받아와서 쓸 것인지 객체로 보낼 것인지 미정
	private CustomerService service3;

	@GetMapping("/chat")
	public String listChat(Model model, Authentication auth) {
		model.addAttribute("chattingroom", service2.findOne(1));
		model.addAttribute("user", service3.findByCu_id(auth.getName()));
		System.out.println("컨트롤러에서 보내는 값 "+service2.findOne(1));
		return "chat/chat.html";
	}

	@ResponseBody
	@RequestMapping("/refreshChat")
	public List<Chat> refreshChat() {
		List<Chat> list = service.findAll(1);
		return list;
	}

	@RequestMapping("/insertChat")
	public ModelAndView insertChat(int cu_no, int cr_no, String ch_content) {
		service.insertChat(cu_no, cr_no, ch_content);
		ModelAndView mav = new ModelAndView("redirect:/listChat");
		return mav;
	}
}
