package com.sist.nono.config;

import java.util.Set;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sist.nono.model.Customer;
import com.sist.nono.model.JoinForm;
import com.sist.nono.repository.CustomerRepository;
import com.sist.nono.service.CustomerService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class joinFormValidator implements Validator {
	
	private final CustomerService repository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return aClass.isAssignableFrom(Customer.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		JoinForm joinForm = (JoinForm)obj;
		//join할 경우 사용되는 제약
			if(repository.findByCu_id(joinForm.getCu_id()).getCu_id()!=null) {
				errors.rejectValue("cu_id","invalid.cu_id",new Object[] {joinForm.getCu_id()},"이미 사용 중인 아이디입니다.");
			}
			if(repository.findByCu_email(joinForm.getCu_email()).getCu_id()!=null) {
				errors.rejectValue("cu_email","invalid.cu_email",new Object[] {joinForm.getCu_email()},"이미 사용 중인 이메일입니다.");
			}
			if(repository.findByCu_nickname(joinForm.getCu_nickname()).getCu_id()!=null) {
				errors.rejectValue("cu_nickname","invalid.cu_nickname",new Object[] {joinForm.getCu_nickname()},"이미 사용 중인 닉네임입니다.");
			}
			if(!(joinForm.getCu_pwd().equals(joinForm.getCu_pwd_check()))) {
				errors.rejectValue("cu_pwd", "invalid.cu_pwd",new Object[] {joinForm.getCu_pwd()},"비밀번호를 다시 확인해주세요.");
			}
			if(!(joinForm.getCu_email_check().equals(joinForm.getCu_email_check_true()))) {
				errors.rejectValue("cu_email_check", "invalid.cu_email_check",new Object[] {joinForm.getCu_email_check_true()},"인증번호를 다시 확인해주세요.");
			}
	}
}
