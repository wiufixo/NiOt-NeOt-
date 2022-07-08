package com.sist.nono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.Feed;
import com.sist.nono.model.FeedComment;
import com.sist.nono.model.User;
import com.sist.nono.service.FeedService;

import lombok.Setter;

@Controller
@Setter
public class FeedController {
	
	@Autowired
	private FeedService f_service;
	
	@GetMapping(value = "/feed/test")
	public String test() {
		
		return "feed/test";
	}
	
	@GetMapping(value = "/feed/list")
	public String findAllFeed(Model model) {
		
		model.addAttribute("list", f_service.findAllFeed());
		
		// /feed/list 를 요청했을때 feedList jsp 로 가서 데이터를 뿌려줘라
		
		return "feed/list";
	}
	
	@GetMapping(value = "/feed/detailFeed/{f_no}")
	public String detalFeed(@PathVariable int f_no, Model model) {
		
		
		
		model.addAttribute("f", f_service.findByIdFeed(f_no));
		
		return "feed/detailList";
		
	}
	
	@GetMapping("/feed/insertForm")
	public String insertForm() {
		
		return "feed/insertForm";
	}
	
	
	@GetMapping("/feed/updateForm/{f_no}")
	public String updateForm(@PathVariable int f_no,Model model) {
	
		model.addAttribute("f", f_service.findByIdFeed(f_no));
		
		return "feed/updateForm";
	}
	
	
	
	@GetMapping("feed/deleteForm/{f_no}")
	public String deleteForm(@PathVariable int f_no) {
		
		f_service.deleteFeed(f_no);
		
		return "redirect:/feed/list";
		
	}
	@GetMapping("/feedComment/updateForm/{fc_no}")
	public String updateCommentForm(@PathVariable int fc_no, Model model) {
		model.addAttribute("fc", f_service.findByIdFeed(fc_no));
		
		return "feed/commentUpdateForm";
	}
	
	 
	
	
}
