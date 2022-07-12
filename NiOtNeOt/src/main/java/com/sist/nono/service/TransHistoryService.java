package com.sist.nono.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.TransHistory;
import com.sist.nono.repository.TransHistoryRepository;


@Service
public class TransHistoryService {

	@Autowired
	private TransHistoryRepository repository;
	
	public List<TransHistory> findAll(){
		return repository.findAll();
	}
	
	public void saveTransHistory(TransHistory t) {
		repository.save(t);
	}
	
	public void deleteTransHistory(int tr_no) {
		repository.deleteById(tr_no);
	}
	
	public Object findById(int tr_no) {
		return repository.findById(tr_no);
	}
	
	public int countTransHistory(int cu_no) {
		return repository.countTransHistory(cu_no);
	}
	
	public ArrayList<TransHistory> findAllByCu_no(int cu_no){
		return repository.findAllByCu_no(cu_no);
	}
	
}
