package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.Customer;
import com.sist.nono.model.RoleType;
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
	
	public void deleteCustomer(int cust_no) {
		repository.deleteById(cust_no);
	}
	
	public Object findById(int cust_no) {
		return repository.findById(cust_no);
	}
	
}
