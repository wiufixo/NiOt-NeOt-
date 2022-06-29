package com.sist.nono.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sist.nono.auth.PrincipalDetailService;


@Configuration // 빈등록
@EnableWebSecurity //필터로 등록 ==> 시큐리티가 활성화되어있는데 관련된 설정들은 해당 파일에서 할 것이다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을하면 권한 및 인증을 미리 체크할 것이다
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService detailService;
	
	@Bean // return값을 스프링이 관리한다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해줄때 pwd를 가로채기를 하는데 해당 pwd가 어떻게 해쉬화되어있는지 알아야 같은 해쉬로 암호화하여 db에 있는 해쉬와 비교가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable() //csrf토큰 비활성화(테스트시 비활성화가편함)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") //시큐리티가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인 해준다
				.defaultSuccessUrl("/");
		http
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true); //로그아웃 페이지들어가면 저장되어 있는 session 모두 파기
			
	}
}
