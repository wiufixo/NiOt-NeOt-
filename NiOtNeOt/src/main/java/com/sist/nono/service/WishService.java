package com.sist.nono.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Wish;
import com.sist.nono.repository.WishRepository;


@Service
public class WishService {

	@Autowired
	private WishRepository repository;
	
	public List<Wish> findAll(){
		return repository.findAll();
	}
	
	public void saveWish(Wish w) {
		repository.save(w);
	}
	
	public void deleteWish(int ws_no) {
		repository.deleteById(ws_no);
	}
	
	public Wish findById(int ws_no) {
		return repository.findById(ws_no).orElseGet(()->new Wish());
	}
	
	public int countWish(int cu_no) {
		return repository.countWish(cu_no);
	}
	
	public ArrayList<Wish> findAllByCu_no(int cu_no){
		return repository.findAllByCu_no(cu_no);
	}
}
