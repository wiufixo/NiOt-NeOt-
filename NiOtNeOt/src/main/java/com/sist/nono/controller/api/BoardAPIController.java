package com.sist.nono.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.dto.ResponseDTO;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.UserRepository;
import com.sist.nono.service.BoardService;
import com.sist.nono.service.UserService;

@RestController
public class BoardAPIController {
	
	@Autowired
	private BoardService service;
	
	private UserService userService;
	
	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestPart(value = "key") Board board, User user, @RequestPart(value = "files",required = false) List<MultipartFile> files) {
		System.out.println(board);
		System.out.println(user);
		System.out.println(files);
		System.out.println("**********************controller");
		if(files==null) {
			service.save(board, user);
		}else {
			service.save(board, user, files);
		}
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
//	@PostMapping("/api/board")
//	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
//		service.save(board, principal.getUser());
//		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
//	}
	
	@DeleteMapping("/api/board/{b_no}")
	public ResponseDTO<Integer> deleteById(@PathVariable int b_no){
		service.deleteById(b_no);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	
	
	@PostMapping("/api/board/{b_no}")
	public ResponseDTO<Integer> update(@PathVariable int b_no, @RequestPart(value = "key") Board board, @RequestPart(value = "files",required = false) List<MultipartFile> files){
		System.out.println("*******update컨트롤러**********");
		System.out.println(b_no);
		System.out.println(board);
		System.out.println(files);
		System.out.println("*****************");
		if(files==null) {
			service.update(b_no, board);
		}else {
			service.update(b_no, board, files);
		}
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
//	@PostMapping("/api/board/{b_no}/comment")
//	public ResponseDTO<Integer> commentSave(@PathVariable int b_no, @RequestBody BoardComment comment, @AuthenticationPrincipal PrincipalDetail principal){
//		service.commentSave(b_no, comment, principal.getUser());
//		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
//	}
	
	@PostMapping("/api/board/{b_no}/comment")
	public ResponseDTO<Integer> commentSave(@PathVariable int b_no, @RequestBody BoardComment comment, User user) {
		service.commentSave(b_no, comment, user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/api/board/{b_no}/comment")
	public ResponseDTO<Integer> commentUpdate(@PathVariable int b_no, @RequestBody BoardComment comment) {
		service.commentUpdate(comment.getBc_no(), comment);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
//	// 위의 메소드와 달리 변수들을 하나의 객체로 전달하여 댓글작성하는법
//	@PostMapping("/api/board/{b_no}/comment")
//	public ResponseDTO<Integer> commentSave(@PathVariable int b_no, @RequestBody BoardComment comment, @AuthenticationPrincipal PrincipalDetail principal){
//		service.commentSave(b_no, comment, principal.getUser());
//		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
//	}
//	
	@DeleteMapping("/api/board/{b_no}/comment/{bc_no}")
	public ResponseDTO<Integer> commentDelete(@PathVariable int bc_no){
		service.commentDelete(bc_no);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
}
