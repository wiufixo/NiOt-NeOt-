package com.sist.nono.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sist.nono.model.Customer;
import com.sist.nono.repository.CustomerRepository;
import com.sist.nono.service.CustomerService;

@Service
public class PrincipalDetailService implements UserDetailsService {
	
	@Autowired
	private CustomerRepository repository;
	
	@Override // 시큐리티가 로그인 요청을 가로챌때 id,pwd 변수 2개를 가로채는데 pwd 부분은 알아서 처리함으로 id부분만 db에 있는지 확인해주면됨
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer principal = repository.findByCu_id(username).orElseThrow(
					()->{return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다!"+username);}
				);
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저정보가 저장됨
	}
}
