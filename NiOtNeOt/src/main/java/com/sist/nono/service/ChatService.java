package com.sist.nono.service;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.Chat;
import com.sist.nono.model.ChatSession;
import com.sist.nono.repository.ChatRepository;

@Service
@ServerEndpoint(value = "/chatt/{cr_no}")
public class ChatService {
	private static Map<Session, Integer> clients = Collections.synchronizedMap(new HashMap<Session, Integer>());
	
	@Autowired
	private ChatRepository repository;

	public List<Chat> findAll(int cr_no) {
		return repository.findAllByCu_no(cr_no);
	}

	public void insertChat(int cu_no, int cr_no, String ch_content) {
		repository.insertChat(cu_no, cr_no, ch_content);
	}

	public void insertChatWithImage(int cu_no, int cr_no, String ch_content, String image) {
		repository.insertChatWithImage(cu_no, cr_no, ch_content, image);
	}
	
	public List<Chat> findRecentChat(int cr_no) {
		return repository.findRecentChat(cr_no);
	}

	public int findUnCheckedChat(int cu_no) {
		return repository.findUnCheckedChat(cu_no);
	}

	@OnOpen
	public void onOpen(Session s, @PathParam("cr_no") int cr_no) {
		if (!clients.containsValue(s)) {
			clients.put(s, cr_no);
			System.out.println("session open : " + s);
		} else {
			System.out.println("이미 연결된 session 임!!!");
		}
	}

	@OnMessage
	public void onMessage(String messageJson, Session s, @PathParam("cr_no") int cr_no) throws Exception {
		System.out.println("receive message : " + messageJson);
		for (Session key : clients.keySet()) {
			Integer value = clients.get(key);
			if(value == cr_no) {
				key.getBasicRemote().sendText(messageJson);
				System.out.println("send data : " + messageJson);
			}
		}
	}

	@OnClose
	public void onClose(Session s) {
		System.out.println("session close : " + s);
		clients.remove(s);
	}
}