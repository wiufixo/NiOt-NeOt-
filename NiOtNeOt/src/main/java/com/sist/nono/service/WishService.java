package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Wish;
import com.sist.nono.repository.WishRepository;


@Service
public class WishService {

	@Autowired
	private WishRepository dao;
	
	public List<Wish> findAll(){
		return dao.findAll();
	}
	
	public void saveWish(Wish w) {
		dao.save(w);
	}
	
	public void deleteWish(int ws_no) {
		dao.deleteById(ws_no);
	}
	
	public Object findById(int ws_no) {
		return dao.findById(ws_no);
	}
	
}
