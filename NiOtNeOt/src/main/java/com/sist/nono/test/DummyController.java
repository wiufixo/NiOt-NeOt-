package com.sist.nono.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;

@RestController
public class DummyController {
	
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{cu_no}")
	public String delete(@PathVariable int cu_no) {
		try {
			userRepository.deleteById(cu_no);
		}catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 유저는 없습니다! no="+cu_no;
		}
		return "삭제되었습니다.no="+cu_no;
	}
	
	@Transactional //메소드 종료시에 자동 commit 
	@PostMapping("/dummy/user/{cu_no}")
	public User updateUser(@PathVariable int cu_no,@RequestBody User user) {
		User r_user = userRepository.findById(cu_no).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다! 해당 유저는 없습니다! no="+cu_no);
		});
		r_user.setCu_id(user.getCu_id());
		r_user.setCu_pwd(user.getCu_pwd());
		r_user.setCu_email(user.getCu_email()); //여기까지 영속화된 r_user의 값이 변경되어서 자동commit되어 db에 값을 변경해준다 ===> 더티체킹
		System.out.println(r_user);
//		userRepository.save(r_user); // 트랜잭션 어노테이션을 달면 save 함수 안하고 update 가능하다.
		return r_user;
	}
	
	//http://localhost:8080/nono/dummy/user
	@GetMapping("/dummy/users")
	public List<User> allUser(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> page = userRepository.findAll(pageable);
		List<User> users = page.getContent(); 
		return users;
	}
	
	
//	//http://localhost:8080/nono/dummy/user/{cu_no}
//	@GetMapping("/dummy/user/{cu_no}")
//	public User user(@PathVariable int cu_no) {
//		return userRepository.findById(cu_no).orElseThrow(new Supplier<IllegalArgumentException>() {
//
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("해당 유저는 없습니다! no="+cu_no);
//			}
//			
//		});
//	}
	//http://localhost:8080/nono/dummy/user/{cu_no}
	@GetMapping("/dummy/user/{cu_no}")
	public User user(@PathVariable int cu_no) {
		User user = userRepository.findById(cu_no).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 없습니다! no="+cu_no);
		});
		return user;
	}
	//http://localhost:8080/nono/dummy/user/{cu_no}
//	@GetMapping("/dummy/user/{cu_no}")
//	public User user(@PathVariable int cu_no) {
//		return userRepository.findById(cu_no).orElseGet(new Supplier<User>() {
//
//			@Override
//			public User get() {
//				return new User();
//			}
//			 
//		});
//	}
	
	
	//http://localhost:8080/nono/dummy/join
//	@PostMapping("/dummy/join")
//	public String join(@RequestParam("cu_id") String cu_id, String cu_pwd, String cu_email) {
//		System.out.println("id:"+cu_id);
//		System.out.println("pwd:"+cu_pwd);
//		System.out.println("email:"+cu_email);
//		return "회원가입을 성공하였습니다!";
//	}
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("no:"+user.getCu_no());
		System.out.println("id:"+user.getCu_id());
		System.out.println("pwd:"+user.getCu_pwd());
		System.out.println("email:"+user.getCu_email());
		System.out.println("regdate:"+user.getCu_created());
		System.out.println("role:"+user.getRole()); // 여기까지 no,regdate,role 입력을 안했음으로 기본값나온다

//	    insert 
//	    into
//	        user
//	        (cu_created, cu_email, cu_id, cu_pwd, role) 
//	    values
//	        (?, ?, ?, ?, ?) ===> 이 쿼리 구문에서 'user'로 default된 role값이 다시 null로 초기화된다
		
		user.setRole(RoleType.USER);
		userRepository.save(user); // 여기서 no,regdate 자동입력되어 입력된다 
		
		return "회원가입을 성공하였습니다!";
	}
	
}
