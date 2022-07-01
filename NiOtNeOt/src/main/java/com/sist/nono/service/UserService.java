package com.sist.nono.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.auth.PrincipalDetail;
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
	
	@Transactional //메소드 종료시 서비스와 트랜잭션 모두 종료 => 자동 commit (더티체킹)
	public void update(User reqUser) { // 영속성 컨테스트 User를 영속화 시키고 그 User를 수정
		User user = repository.findById(reqUser.getCu_no()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		if(user.getOauth() == null || user.getOauth().equals("")) { // oauth 로그인사용자는 비밀번호 수정 불가능하게 한다
			String rawPwd = reqUser.getCu_pwd();
			String encPwd = encoder.encode(rawPwd);
			user.setCu_pwd(encPwd);
			user.setCu_email(reqUser.getCu_email());
		}
	}
	
	@Transactional(readOnly = true)
	public User findById(String cu_id) {
		User user = repository.findByCu_id(cu_id).orElseGet(()->new User());
		return user;
	}
	
//	@Transactional(readOnly = true) //트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
//	public User login(User user) {
//		return repository.login(user.getCu_id(), user.getCu_pwd());
//	}
}
