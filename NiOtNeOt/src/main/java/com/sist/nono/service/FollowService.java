package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
}
