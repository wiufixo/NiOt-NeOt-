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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardFile;
import com.sist.nono.paging.PaginationInfo;
import com.sist.nono.service.BoardFileService;
import com.sist.nono.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private BoardFileService fileService;
	
	@GetMapping("/board/list")
	public String list(Model model, PaginationInfo pagination) {
		pagination.setTotalRecordCnt(service.getTotalRecordCnt());
		model.addAttribute("board", service.findAll(pagination.getStartIndex(),pagination.getPageSize()));
		model.addAttribute("pagination", pagination);
		System.out.println(pagination.toString());
		return "board/list";
	}
//	@GetMapping("/board/list")
//	public String list(Model model, PaginationInfo pagination) {
//		pagination.setTotalRecordCnt(service.getTotalRecordCnt(pagination));
//		//쿼리스트링으로 전달된 값은 자동 set 된다
//		model.addAttribute("board", service.findAll(pagination));
//		model.addAttribute("pagination", pagination);
//		System.out.println(pagination.toString());
//		return "board/list";
//	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
	
	@GetMapping("/board/{page}/{b_no}")
	public String findById(@PathVariable int page, @PathVariable int b_no, Model model) {
		PaginationInfo pagination = new PaginationInfo();
		pagination.setPage(page);
		pagination.setTotalRecordCnt(service.getTotalRecordCnt());
		
		service.increaseHit(b_no);
		
		model.addAttribute("fileList", fileService.findAllByBoard(b_no)) ;
		
		model.addAttribute("page", page);
		model.addAttribute("board",service.findById(b_no));
		return "board/detail";
	}
	
	@GetMapping("/board/download")
	public void downloadAttachFile(@RequestParam(value = "bf_no", required = false) int bf_no, Model model, HttpServletResponse response) {

		if (bf_no == 0) throw new RuntimeException("올바르지 않은 접근입니다.");

		BoardFile fileInfo = fileService.findById(bf_no);
		
		if (fileInfo == null) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}

		String uploadDate = fileInfo.getBf_created().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get("C:", "develop", "upload", uploadDate).toString();

		String filename = fileInfo.getOriginal_name();
		File file = new File(uploadPath, fileInfo.getSave_name());

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
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}
	
	
	
	
	@GetMapping("/board/{page}/{b_no}/updateForm")
	public String updateForm(@PathVariable int page, @PathVariable int b_no, Model model) {
		model.addAttribute("board",service.findById(b_no));
		model.addAttribute("page", page);

		List<BoardFile> fileList = fileService.findAllByBoard(b_no);
		model.addAttribute("fileList", fileList);
		
		return "board/updateForm";
	}
	
	@GetMapping("/fileList")
	@ResponseBody
	public List<BoardFile> fileList(int b_no){
		return fileService.findAllByBoard(b_no);
	}
}
