package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.LoginList;
import com.sist.nono.repository.LoginListRepository;


@Service
public class LoginListService {

	@Autowired
	private LoginListRepository repository;
	
	public List<LoginList> findAll(){
		return repository.findAll();
	}
	
	public void saveLoginList(LoginList l) {
		repository.save(l);
	}
	
	public void deleteLoginList(int ll_no) {
		repository.deleteById(ll_no);
	}
	
	public Object findById(int ll_no) {
		return repository.findById(ll_no);
	}
	
}
