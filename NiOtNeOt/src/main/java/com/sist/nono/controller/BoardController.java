package com.sist.nono.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.Board;
import com.sist.nono.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("/")
	public String index(Model model, @PageableDefault(size = 3, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Board> paging = service.findAll(pageable);
		model.addAttribute("board",paging);
		// /WEB-INF/views/index.jsp
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{b_no}")
	public String findById(@PathVariable int b_no, Model model) {
		model.addAttribute("board",service.findById(b_no));
		return "board/detail";
	}
	
	@GetMapping("/board/{b_no}/updateForm")
	public String updateForm(@PathVariable int b_no, Model model) {
		model.addAttribute("board",service.findById(b_no));
		return "board/updateForm";
	}
}
