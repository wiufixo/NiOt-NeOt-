package com.sist.nono.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sist.nono.model.Customer;

import lombok.Getter;

//시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료후 UserDetails 타입의 오브젝트를 고유 세션에 저장한다
@Getter
public class PrincipalDetail implements UserDetails{
	
	private Customer customer; //composition(has a 관계)
	
	public PrincipalDetail(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String getPassword() {
		return customer.getCu_pwd();
	}

	@Override
	public String getUsername() {
		return customer.getCu_id();
	}

	@Override // true ===> 계정 만료되지않음
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override // true ===> 계정 잠기지않음
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override // true ==> 비밀번호가 만료되지않음
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override // true ==> 계정이 활성화됨
	public boolean isEnabled() {
		return true;
	}
	
	@Override // 계정이 갖고있는 권한 목록을 리턴
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> colletors = new ArrayList<>();
		colletors.add(()->{return "ROLE_"+customer.getRole();
		});
		return colletors;
	}
	
}
