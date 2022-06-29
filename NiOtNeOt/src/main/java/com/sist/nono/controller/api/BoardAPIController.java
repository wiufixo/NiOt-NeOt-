package com.sist.nono.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.dto.ResponseDTO;
import com.sist.nono.model.Board;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;
import com.sist.nono.service.BoardService;
import com.sist.nono.service.UserService;

@RestController
public class BoardAPIController {
	
	@Autowired
	private BoardService service;
	
	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		service.save(board, principal.getUser());
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/board/{b_no}")
	public ResponseDTO<Integer> deleteById(@PathVariable int b_no){
		service.deleteById(b_no);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PostMapping("/api/board/{b_no}")
	public ResponseDTO<Integer> update(@PathVariable int b_no, @RequestBody Board board){
		service.update(b_no, board);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
}
