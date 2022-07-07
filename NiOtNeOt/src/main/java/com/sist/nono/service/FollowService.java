package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Follow;
import com.sist.nono.repository.FollowRepository;


@Service
public class FollowService {

	@Autowired
	private FollowRepository repository;
	
	public List<Follow> findAll(){
		return repository.findAll();
	}
	
	public void saveFollow(Follow f) {
		repository.save(f);
	}
	
	public void deleteFollow(int fo_no) {
		repository.deleteById(fo_no);
	}
	
	public Object findById(int fo_no) {
		return repository.findById(fo_no);
	}
	
	public int countFollower(int cu_no) {
		return repository.countFollower(cu_no);
	}
	
	public int countFollow(int cu_no) {
		return repository.countFollow(cu_no);
	}
	
	public int checkFollow(int cu_no, int user_no) {
		return repository.checkFollow(cu_no, user_no);
	}
	
	public int findFo_no(int cu_no, int user_no) {
		return repository.checkFollow(cu_no, user_no);
	}
	
	public List<Follow> findByFollower(int cu_no) {
		return repository.findByFollower(cu_no);
	}
	
	public List<Follow> findByFollowed(int cu_no) {
		return repository.findByFollowed(cu_no);
	}
}
