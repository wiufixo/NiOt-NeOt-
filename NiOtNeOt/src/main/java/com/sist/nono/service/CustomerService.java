package com.sist.nono.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.Customer;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int join(Customer c) {
		String rawPwd = c.getCu_pwd();
		String encPwd = encoder.encode(rawPwd); //해쉬화
		c.setCu_pwd(encPwd);
		c.setRole(RoleType.USER);
		repository.save(c);
		return 1;
	}
	
	
	public List<Customer> findAll(){
		return repository.findAll();
	}
	
	public void saveCustomer(Customer c) {
		repository.save(c);
	}
	
	public void deleteCustomer(int cu_no) {
		repository.deleteById(cu_no);
	}
	
	public Customer findById(int cu_no) {
		return repository.findById(cu_no).orElseGet(()->new Customer());
	}
	
	public Customer findByCu_id(String cu_id) {
		return repository.findByCu_id(cu_id).orElseGet(()->new Customer());
	}
	
	public Customer findByCu_email(String cu_email) {
		return repository.findByCu_email(cu_email).orElseGet(()->new Customer());
	}
	
	public Customer findByCu_nickCustomer(String cu_nickname) {
		return repository.findByCu_nickname(cu_nickname).orElseGet(()->new Customer());
	}
}
