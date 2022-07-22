package com.sist.nono.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.dto.ChatRoomDTO;
import com.sist.nono.model.ChatRoom;
import com.sist.nono.repository.ChatRoomRepository;
import com.sist.nono.repository.ChatRoomRepository2;

@Service  
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository repository;

	public List<ChatRoom> findAll(int cu_no) {
		return repository.findAllByCu_no(cu_no);
	}
	
	public void save(ChatRoom cr) {
		repository.save(cr);
	}
	
	public String create(int cu_no, int bcu_no, int pr_no) {
		repository.createChatRoom(cu_no, bcu_no, pr_no);
		return "채팅방 생성 완료";
	}
	
	public void delete(int cr_no) {
		repository.deleteById(cr_no);
	}
	
	public ChatRoom findOne(int cr_no) {
		return repository.findById(cr_no).orElseGet(()->new ChatRoom());
	}

	public String findBcuidByCrno(int cr_no) {
		return repository.findBcuidByCrno(cr_no);
	}
	
	public void updateRoomTrade(int cr_no) {
		repository.updateRoomTrade(cr_no);
	}
}
