package com.sist.nono.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sist.nono.model.Alert;
import com.sist.nono.repository.AlertRepository;

@Service
public class AlertService { 
	@Autowired
	private AlertRepository repository;
	
	public List<Alert> findAllByCu_no(int cu_no) {
		return repository.findAllByCu_no(cu_no);
	}
	
	public void save(Alert a) {
		repository.save(a);
	}
	
	public Alert findById(int al_no) {
		return repository.getOne(al_no);
	}
	
	public void delete(int al_no) {
		repository.deleteById(al_no);
	}
	
	public int findUnCheckedAlert(int cu_no) {
		return repository.findUncheckedAlert(cu_no);
	}
}
