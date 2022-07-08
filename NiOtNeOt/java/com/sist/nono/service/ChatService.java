package com.sist.nono.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Chat;
import com.sist.nono.repository.ChatRepository;

@Service
@ServerEndpoint(value="/chatt")
public class ChatService {
	@Autowired
	private ChatRepository repository;

	public List<Chat> findAll(int cr_no) {
		return repository.findAllByCu_no(cr_no);
	}
	
	public void insertChat(int cu_no, int cr_no, String ch_content) {
		repository.insertChat(cu_no, cr_no, ch_content);
	}
	
	public Chat getOne(int ch_no) {
		return repository.getOne(ch_no);
	}
	
	public int findUnCheckedChat(int cu_no) {
		return repository.findUnCheckedChat(cu_no);
	}
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	
	@OnOpen
	public void onOpen(Session s) {
		System.out.println("open session : " + s.toString());
		if(!clients.contains(s)) {
			clients.add(s);
			System.out.println("session open : " + s);
		}else {
			System.out.println("이미 연결된 session 임!!!");
		}
	}
	
	
	@OnMessage
	public void onMessage(String msg, Session session) throws Exception{
		System.out.println("receive message : " + msg);
		for(Session s : clients) {
			System.out.println("send data : " + msg);
			s.getBasicRemote().sendText(msg);

		}
		
	}
	
	@OnClose
	public void onClose(Session s) {
		System.out.println("session close : " + s);
		clients.remove(s);
	}
}