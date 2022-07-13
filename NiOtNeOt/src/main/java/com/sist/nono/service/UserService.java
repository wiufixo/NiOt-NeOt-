package com.sist.nono.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.exception.CustomException;
import com.sist.nono.exception.ErrorCode;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public User save(User user) {
		String rawPwd = user.getCu_pwd();
		String encPwd = encoder.encode(rawPwd); //해쉬화
		user.setCu_pwd(encPwd);
		user.setRole(RoleType.USER);
		return userRepository.save(user);
	}
	
	
	@Transactional
	public int update(User user) {
		System.out.println(user);
		String rawPwd = user.getCu_pwd();
		String encPwd = encoder.encode(rawPwd); //해쉬화
		return userRepository.update(encPwd, user.getCu_email(), user.getCu_no());
	}

	
//	@Transactional
//	public void resign(User user) {
//		System.out.println(user.getCu_id());
//		System.out.println(user.getCu_pwd());
//		String rawPwd = user.getCu_pwd();
//		String encPwd = encoder.encode(rawPwd); //해쉬화
//		System.out.println(encPwd);
//		
//		int re = userRepository.findUser(user.getCu_id(), encPwd);
//		if(re==1) {
//			userRepository.deleteById(user.getCu_no());
//			System.out.println("유저 삭제 완료!");
//		}
//	}
}
