package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Address;
import com.sist.nono.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;
	
	public List<Address> findAll(){
		return repository.findAll();
	}
	
	public void saveAddress(Address a) {
		repository.save(a);
	}
	
	public void deleteAddress(int cust_no) {
		repository.deleteById(cust_no);
	}
	
	public Address findById(int cust_no) {
		return repository.findById(cust_no).orElseGet(()->new Address());
	}

}
