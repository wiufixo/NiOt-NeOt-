package com.sist.nono.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int join(User user) {
		String rawPwd = user.getCu_pwd();
		String encPwd = encoder.encode(rawPwd); //해쉬화
		user.setCu_pwd(encPwd);
		user.setRole(RoleType.USER);
		repository.save(user);
		return 1;
	}
	
//	@Transactional(readOnly = true) //트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
//	public User login(User user) {
//		return repository.login(user.getCu_id(), user.getCu_pwd());
//	}
}
