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
	
	public void save(Chat c) {
		repository.save(c);
	}
	
	public Chat getOne(int ch_no) {
		return repository.getOne(ch_no);
	}
	
	public int findUnCheckedChat(int cu_no) {
		return repository.findUnCheckedChat(cu_no);
	}
}
