package com.sist.nono.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Category;
import com.sist.nono.repository.CategoryRepository;

import lombok.Setter;

@Service
@Setter
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional
	public List<Category> findAllCa(){
		return repository.findAll();
	}
	
}
