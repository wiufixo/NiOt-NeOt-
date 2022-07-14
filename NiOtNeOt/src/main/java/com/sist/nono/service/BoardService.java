package com.sist.nono.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.dao.BoardDao;
import com.sist.nono.dto.BoardDto;
import com.sist.nono.dto.FileUploadDTO;
import com.sist.nono.exception.CustomException;
import com.sist.nono.exception.ErrorCode;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardFile;
import com.sist.nono.model.Customer;
import com.sist.nono.paging.CommonParams;
import com.sist.nono.paging.Pagination;
import com.sist.nono.repository.BoardFileRepository;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardFileRepository fileRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BoardDao dao;

	@Autowired
	private FileUploadDTO fu;


	@Transactional
	public void delete(int b_no) {
		boardRepository.deleteById(b_no);
	}


	@Transactional
	public void update(Board r_board, List<MultipartFile> files, List<Integer> fileNo) {

		int b_no = r_board.getB_no();
		Board board = boardRepository.findById(b_no)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		board.setB_title(r_board.getB_title());
		board.setB_content(r_board.getB_content());
		boardRepository.update(board.getB_title(), board.getB_content(), board.getB_no());

		List<BoardFile> bf = fileRepository.findAllByBoard(b_no);
		for(BoardFile file : bf) {
			int bf_no = file.getBf_no();
			if(!fileNo.contains(bf_no)) {
				fileRepository.deleteById(bf_no);
			}
		}
		
		List<BoardFile> fileList = fu.uploadFiles(files, b_no);
		if (CollectionUtils.isEmpty(fileList) == false) {
			for(BoardFile file : fileList) {
				fileRepository.save(file);
			}
		}
	}


	@Transactional
	public void save(Board board, List<MultipartFile> files, Customer customer) {
	//	board.setUser(userRepository.findById(2).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
		board.setCustomer(customer);
		Board b = boardRepository.save(board);
		List<BoardFile> fileList = fu.uploadFiles(files, b.getB_no()); //공파일을 제외한 multipartfile 리스트 반환
		if (CollectionUtils.isEmpty(fileList) == false) {
			for(BoardFile bf : fileList) {
				fileRepository.save(bf);
			}
		}
	}
	
	
	@Transactional
	public void increaseHit(int b_no) {
		boardRepository.increaseHit(b_no);
	}

	
	@Transactional(readOnly = true)
	public Board getBoard(int b_no) {
		return boardRepository.findById(b_no).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
	}


	/**
	 * 게시글 리스트 조회 - (With. pagination information)
	 */
	public Map<String, Object> findAll(CommonParams params) {

		Map<String, Object> response = new HashMap<>();
		//response.params.pagination.totalRecordCount
		if(params.getSearchType() != null) {
			if(params.getSearchType().equals("bc")) {
				if (dao.countComment(params) < 1) {
					Pagination pagination = new Pagination(0, params);
					params.setPagination(pagination);
					response.put("params", params);
					response.put("list", Collections.emptyList());
					return response;
				}
				int count = dao.countComment(params);
				Pagination pagination = new Pagination(count, params);
				params.setPagination(pagination);
				List<BoardDto> list1 = dao.findComment(params);
				Map<Integer, BoardDto> map = new HashMap<Integer, BoardDto>();
				for(BoardDto b : list1) {
					map.put(b.getB_no(), b);
				}

				List<BoardDto> list = new ArrayList<>(map.values());

				response.put("params", params);
				response.put("list", list);
				return response;
			}
		}
		
		// 게시글 수 조회
		int count = dao.count(params);
		
		// 등록된 게시글이 없는 경우, 로직 종료
		if (count < 1) {
			Pagination pagination = new Pagination(0, params);
			params.setPagination(pagination);
			response.put("params", params);
			response.put("list", Collections.emptyList());
			return response;
		}
		// 페이지네이션 정보 계산
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);

		// 게시글 리스트 조회
		List<BoardDto> list = dao.findAll(params);

		// 데이터 반환
		response.put("params", params);
		response.put("list", list);

		return response;
	}


	public List<Board> findAllByCu_no(int user_no) {
		return boardRepository.findAllByCu_no(user_no);
	}
}