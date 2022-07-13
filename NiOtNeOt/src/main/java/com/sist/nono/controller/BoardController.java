package com.sist.nono.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardFile;
import com.sist.nono.paging.CommonParams;
import com.sist.nono.service.BoardCommentService;
import com.sist.nono.service.BoardFileService;
import com.sist.nono.service.BoardService;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardCommentService commentService;
	
	@Autowired
	private BoardFileService fileService;


	@GetMapping("/download")
	public void downloadAttachFile(@RequestParam(value = "bf_no", required = false) int bf_no, Model model, HttpServletResponse response) {

		if (bf_no == 0)
			throw new RuntimeException("올바르지 않은 접근입니다.");

		BoardFile fileInfo = fileService.findById(bf_no);

		String uploadDate = fileInfo.getBf_created().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
		String uploadPath = Paths.get("C:", "develop", "upload", uploadDate).toString();

		String filename = fileInfo.getOriginal_name();
		File file = new File(uploadPath, fileInfo.getSave_name());

		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException("파일 다운로드에 실패하였습니다.");

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}

	
	@ResponseBody
	@DeleteMapping("/delete/{b_no}")
	public String delete(@PathVariable int b_no) {
		boardService.delete(b_no);
		return "delete OK";
	}

	
	@GetMapping("/detail/{b_no}")
	public String detail(Model model, @PathVariable int b_no, CommonParams params, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.increaseHit(b_no);
		model.addAttribute("board", boardService.getBoard(b_no));
		model.addAttribute("comments", commentService.findAll(b_no));
		model.addAttribute("params", params);
		model.addAttribute("fileList", fileService.findAllByBoard(b_no));
		model.addAttribute("principal",principal);
		return "board/detail";
	}

	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(defaultValue = "0") int b_no, CommonParams params) {
		if (b_no == 0) {
			model.addAttribute("board", new Board());
			model.addAttribute("fileList", new ArrayList<BoardFile>());
		} else {
			model.addAttribute("board", boardService.getBoard(b_no));
			model.addAttribute("fileList", fileService.findAllByBoard(b_no));
		}
		model.addAttribute("params", params);

		return "board/form";
	}


	@PostMapping("/form")
	public String formSubmit(Board board, @RequestParam(required = false) List<MultipartFile> files, @RequestParam(required = false) List<Integer> fileNo, @AuthenticationPrincipal PrincipalDetail principal, BindingResult bindingResult) {

		/*
		 * boardValidator.validate(board, bindingResult); //validator 객체로 유효성 검사
		 * if(bindingResult.hasErrors()) { System.out.println("*** board error 발생!!");
		 * return
		 * "@{/board/form(page=${response.params.page},searchType=${response.params.searchType},keyword=${response.params.keyword})}";
		 * 
		 * return "board/form"; }
		 */

		if(CollectionUtils.isEmpty(files)) {
			files = Collections.emptyList();
		}
		
		if(CollectionUtils.isEmpty(fileNo)) {
			fileNo = Collections.emptyList();
		}

		if (board.getB_no() == 0) { // 새글 작성
			for(int i=0; i<files.size(); i++) {
				log.debug("================== file start ==================");
				log.debug("파일 이름: "+files.get(i).getName());
				log.debug("파일 실제 이름: "+files.get(i).getOriginalFilename());
				log.debug("파일 크기: "+files.get(i).getSize());
				log.debug("content type: "+files.get(i).getContentType());
				log.debug("================== file   END ==================");
			}
			log.debug(principal.getUsername());
			boardService.save(board, files, principal.getCustomer());

		} else { // 글 수정
			for(int i=0; i<files.size(); i++) {
				log.debug("================== file start ==================");
				log.debug("파일 이름: "+files.get(i).getName());
				log.debug("파일 실제 이름: "+files.get(i).getOriginalFilename());
				log.debug("파일 크기: "+files.get(i).getSize());
				log.debug("content type: "+files.get(i).getContentType());
				log.debug("================== file   END ==================");
			}
			boardService.update(board, files, fileNo);
		}

		return "redirect:/board/list";
	}

	@GetMapping("/list")
	public String list(Model model, CommonParams params) {
		model.addAttribute("response", boardService.findAll(params));
		return "board/list";
	}

}
