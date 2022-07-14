package com.sist.nono.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.Feed;
import com.sist.nono.model.FeedComment;
import com.sist.nono.model.FeedImg;

import com.sist.nono.service.FeedFileService;
import com.sist.nono.service.FeedService;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import lombok.Setter;

@Controller
@Setter
public class FeedController {
	
	@Autowired
	private FeedService f_service;
	
	@Autowired
	private FeedFileService fi_Service;
	
	@GetMapping(value = "/feed/test")
	public String test() {
		
		return "feed/layout/basic2";
	}
	
	@GetMapping(value = "/feed/list")
	public String findAllFeed(Model model) {
		
		model.addAttribute("list", f_service.findAllFeed());
		
		// /feed/list 를 요청했을때 feedList jsp 로 가서 데이터를 뿌려줘라
		
		return "feed/list";
	}
	
	@GetMapping(value = "/feed/detailFeed/{f_no}")
	public String detalFeed(@PathVariable int f_no, Model model) {
		
		f_service.increaseHit(f_no);
		
		model.addAttribute("fileList", fi_Service.findAllByFeed(f_no));
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
		
		List<FeedImg> fileList = fi_Service.findAllByFeed(f_no);
		
		model.addAttribute("fileList", fileList);
		
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
	
//-------------------------------------------------------------------------------
	
	@GetMapping("/feed/download")
	public void downloadAttachFile(@RequestParam(value = "fi_no",required = false) int fi_no , Model model , HttpServletResponse response) {
		
		if(fi_no == 0) throw new RuntimeException("올바르지 않은 접근입니다.");
		
		FeedImg fileInfoFeedImg = fi_Service.findById(fi_no);
		
		if(fileInfoFeedImg == null) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
			
		}
		//오늘 날짜
		String uploadDate = fileInfoFeedImg.getFi_created().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
		
		//업로드 경로
		String uploadPathString = Paths.get("C:","develop","upload",uploadDate).toString();
		
		String filename = fileInfoFeedImg.getOriginal_name();
		File file = new File(uploadPathString,fileInfoFeedImg.getSave_name());
		
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
			
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("시스템 문제가 발생하였습니다.");
		}
		
	}
	
	//ajax 통신을 위해서 ResponseBody 어노케이션을 붙이고 json 타입으로 반환.
	@GetMapping("/imgList")
	@ResponseBody
	public List<FeedImg> fileList(int f_no) {
		//해당 피드에 담긴 이미지의 정보를 리스트로 가져온다.
		return fi_Service.findAllByFeed(f_no);
		
	}
	
	
}
