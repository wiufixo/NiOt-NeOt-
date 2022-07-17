package com.sist.nono.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

	@GetMapping("/chat/{cr_no}")	//	채팅방 번호와 유저 ID를 상태유지하여 채팅창으로 보낸다 
	public String listChat(Model model, Authentication auth,@PathVariable int cr_no) {
		model.addAttribute("chattingroom", service2.findOne(cr_no));
		model.addAttribute("user", service3.findByCu_id(auth.getName()));
		return "chat/chat.html";
	}

	@ResponseBody
	@RequestMapping("/refreshChat")
	public List<Chat> refreshChat(int cr_no) {
		List<Chat> list = service.findAll(cr_no);
		return list;
	}
	
	@ResponseBody
	@PostMapping("insertChat")
	public String insertChat(int cu_no, int cr_no, String ch_content) {
		service.insertChat(cu_no, cr_no, ch_content);
		return "메시지 DB로 송신";
	}
	
	@ResponseBody
	@RequestMapping("/findRecentChat")
	public List<Chat> findRecentChat(int cr_no) {
		List<Chat> chat = service.findRecentChat(cr_no);
		System.out.println("findRecentChat 컨트롤러에서 보내는 값 : " + chat);
		return chat;
	}
	
	@ResponseBody
	@PostMapping("insertChatWithImage")
	public String insertWithImage(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest session) {
		String uploadFolder = "./src/main/resources/static/image/chat";
		UUID uuid = UUID.randomUUID();

		String uploadFileName = uploadFile.getOriginalFilename();
		uploadFileName = uuid.toString() + uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

		int cu_no = Integer.parseInt(session.getParameter("cu_no"));
		int cr_no = Integer.parseInt(session.getParameter("cr_no"));
		String ch_content = (session.getParameter("ch_content"));

		// 파일 다운로드
		File saveFile = new File(uploadFolder, uploadFileName);
		try {
			uploadFile.transferTo(saveFile);
		} catch (Exception e) {
			System.out.println("에러:" + e.getMessage());
		}

		service.insertChatWithImage(cu_no, cr_no, ch_content,uploadFileName);

		return "사진 포함 메시지 DB로 송신";
	}
}
