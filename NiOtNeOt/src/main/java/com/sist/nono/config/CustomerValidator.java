package com.sist.nono.config;

import java.util.Set;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sist.nono.model.Customer;
import com.sist.nono.repository.CustomerRepository;
import com.sist.nono.service.CustomerService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerValidator implements Validator {
	
	private final CustomerService repository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return aClass.isAssignableFrom(Customer.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Customer customer = (Customer)obj;
		System.out.println("go");
		System.out.println(customer.getCu_id());
		System.out.println("go");
		if(repository.findByCu_id(customer.getCu_id()).getCu_id()!=null) {
			errors.rejectValue("cu_id","invalid.cu_id",new Object[] {customer.getCu_id()},"이미 사용 중인 아이디입니다.");
		}
		if(repository.findByCu_email(customer.getCu_email()).getCu_id()!=null) {
			errors.rejectValue("cu_email","invalid.cu_email",new Object[] {customer.getCu_email()},"이미 사용 중인 이메일입니다.");
		}
		if(repository.findByCu_nickname(customer.getCu_nickname()).getCu_id()!=null) {
			errors.rejectValue("cu_nickname","invalid.cu_nickname",new Object[] {customer.getCu_nickname()},"이미 사용 중인 닉네임입니다.");
		}
	}
}
