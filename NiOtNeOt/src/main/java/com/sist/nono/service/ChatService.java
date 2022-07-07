package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sist.nono.model.Chat;
import com.sist.nono.repository.ChatRepository;

@Service
public class ChatService {
	@Autowired
	private ChatRepository repository;

	public List<Chat> findAll(int cu_no) {
		return repository.findAllByCu_no(cu_no);
	}
	
	public void insertChat(int cu_no, int cr_no, int pr_no, String ch_content) {
		System.out.println("채팅 서비스 인서트");
		repository.insertChat(cu_no, cr_no, pr_no, ch_content);
	}
	
	public Chat getOne(int ch_no) {
		return repository.getOne(ch_no);
	}
	
	public int findUnCheckedChat(int cu_no) {
		return repository.findUnCheckedChat(cu_no);
	}
}
