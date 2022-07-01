package com.sist.nono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sist.nono.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
