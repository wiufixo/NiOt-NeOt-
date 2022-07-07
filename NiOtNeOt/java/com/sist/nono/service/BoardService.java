package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.dto.FileUploadDTO;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;
import com.sist.nono.model.BoardFile;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.paging.PaginationInfo;
import com.sist.nono.repository.BoardCommentRepository;
import com.sist.nono.repository.BoardFileRepository;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository repository;
	
	@Autowired
	private BoardCommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardFileRepository fileRepository;
	
	@Autowired
	private FileUploadDTO fu;
	
	//!!!!송승민 만듦!!!
	public List<Board> findAllBoardByCu_no(int cu_no){
		return repository.findAllByCu_no(cu_no);
	}
	//
	
	@Transactional
	public Board save(Board board, User user) {
		user = userRepository.findById(2).get();
		board.setB_hit(0);
		board.setB_ref(board.getB_no());
		board.setB_step(0);
		board.setB_level(0);
		board.setUser(user);
		Board b = repository.save(board);
		int b_ref = b.getB_no();
		b.setB_ref(b_ref);
//		System.out.println(board);
//		System.out.println(user);
//		System.out.println("**********************save1");
		return b; 
	}
	
	@Transactional
	public void save(Board board, User user, List<MultipartFile> files) {
		
		Board b = save(board, user);
		if(b != null) {
			List<BoardFile> fileList = fu.uploadFiles(files, b.getB_no());
			System.out.println(fileList);
			if (CollectionUtils.isEmpty(fileList) == false) {
				for(BoardFile bf : fileList) {
					fileRepository.save(bf);
					System.out.println("bf="+bf);
				}
			}
		}
		
//		System.out.println(board);
//		System.out.println(b);
//		System.out.println(user);
//		System.out.println(files);
//		System.out.println("**********************save2");
		
//		user = userRepository.findById(2).get();
//		board.setB_hit(0);
//		board.setB_ref(board.getB_no());
//		board.setB_step(0);
//		board.setB_level(0);
//		board.setUser(user);
//		int b_ref = repository.save(board).getB_no();
//		board.setB_ref(b_ref);
	}
	
	@Transactional
	public void update(int b_no, Board r_board, List<MultipartFile> files) {
		
		Board board = repository.findById(b_no)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		board.setB_title(r_board.getB_title());
		board.setB_content(r_board.getB_content());
		
		int re = repository.boardUpdate(board.getB_title(), board.getB_content(), board.getB_no());
		if(re==1) {
			String isFileChanged = r_board.getIsFileChanged();
			
			if(isFileChanged.equals("Y")) {
				if(board.getBoardFile() != null) {
//					fileRepository.deleteFile(b_no);
					
				}
			}
			List<BoardFile> fileList = fu.uploadFiles(files, b_no);
			if (CollectionUtils.isEmpty(fileList) == false) {
				board.setBoardFile(fileList);
//				for(BoardFile bf : fileList) {
//					fileRepository.save(bf);
//					System.out.println("bf="+bf);
//				}
			}
		}
		System.out.println("*******3개서비스**********");
	} 
	@Transactional
	public void update(int b_no, Board r_board) {
		
		Board board = repository.findById(b_no)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		board.setB_title(r_board.getB_title());
		board.setB_content(r_board.getB_content());
		int re = repository.boardUpdate(board.getB_title(), board.getB_content(), board.getB_no());
		
		if(re==1) {
			String isFileChanged = r_board.getIsFileChanged();
			
			if(isFileChanged.equals("Y")) {
				fileRepository.deleteFile(b_no);
			}
		}
		System.out.println("*******2개서비스**********");
		System.out.println(board);
		System.out.println("*****************");
	} 
	
//	@Transactional
//	public void save(Board board, User user) {
//		board.setB_hit(0);
//		board.setB_ref(board.getB_no());
//		board.setB_step(0);
//		board.setB_level(0);
//		board.setUser(user);
//		int b_ref = repository.save(board).getB_no();
//		board.setB_ref(b_ref);
//	}

//	@Transactional(readOnly = true)
//	public List<Board> findAll(PaginationInfo pagination) {
//		
//		List<Board> list = null;
//		
//		if(pagination.getSearchType().equals("tc")) {
//			list = repository.searchByTitleAndContent(pagination.getSearchKeyword(), pagination.getStartIndex(), pagination.getPageSize());
//		}else if(pagination.getSearchType().equals("t")) {
//			list = repository.searchByTitle(pagination.getSearchKeyword(), pagination.getStartIndex(), pagination.getPageSize());
//		}else if(pagination.getSearchType().equals("c")) {
//			list = repository.searchByContent(pagination.getSearchKeyword(), pagination.getStartIndex(), pagination.getPageSize());
//		}else if(pagination.getSearchType().equals("w")) {
//			list = repository.searchByUser(pagination.getSearchKeyword(), pagination.getStartIndex(), pagination.getPageSize());
//		}else if(pagination.getSearchType().equals("bc")) {
//			list = repository.searchByComment(pagination.getSearchKeyword(), pagination.getStartIndex(), pagination.getPageSize());
//		}else {
//			list = repository.selectBoardList(pagination.getStartIndex(), pagination.getPageSize());
//		}
//		return list;
//	}
	@Transactional(readOnly = true)
	public List<Board> findAll(int startIndex, int pageSize) {
		return repository.selectBoardList(startIndex, pageSize);
	}
	
	@Transactional(readOnly = true)
	public int getTotalRecordCnt() {
		return repository.getTotalRecordCnt();
	}
//	@Transactional(readOnly = true)
//	public int getTotalRecordCnt(PaginationInfo pagination) {
//		
//		int tot = 0;
//		
//		if(pagination.getSearchType().equals("tc")) {
//			tot = repository.getTotalRecordCnt1(pagination.getSearchKeyword());
//		}else if(pagination.getSearchType().equals("t")) {
//			tot = repository.getTotalRecordCnt2(pagination.getSearchKeyword());
//		}else if(pagination.getSearchType().equals("c")) {
//			tot = repository.getTotalRecordCnt3(pagination.getSearchKeyword());
//		}else if(pagination.getSearchType().equals("w")) {
//			tot = repository.getTotalRecordCnt4(pagination.getSearchKeyword());
//		}else if(pagination.getSearchType().equals("bc")) {
//			tot = repository.getTotalRecordCnt5(pagination.getSearchKeyword());
//		}else {
//			tot = repository.getTotalRecordCnt();
//		}
//		return tot;
//	}

	
	@Transactional
	public void increaseHit(int b_no) {
		repository.increaseHit(b_no);
	}
	
	
	@Transactional(readOnly = true)
	public Board findById(int b_no) {
		return repository.findById(b_no).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패: 글번호를 찾을 수 없습니다!");
			});
	}
	
	@Transactional
	public void deleteById(int b_no) {
		repository.deleteById(b_no);
	}
	
	
	@Transactional
	public void commentSave(int b_no, BoardComment comment, User user) {
		user = userRepository.findById(3).get();
		comment.setUser(user);
		comment.setBoard(repository.findById(b_no).orElseThrow(()->{
					return new IllegalArgumentException("댓글의 게시글 찾기 실패!");
				}));
		comment.setBc_step(0);
		comment.setBc_level(0);
		int b_ref = commentRepository.save(comment).getBc_no();
		comment.setBc_ref(b_ref);
	}
	@Transactional
	public void commentUpdate(int bc_no, BoardComment r_comment) {
		BoardComment comment= commentRepository.findById(bc_no)
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		comment.setBc_content(r_comment.getBc_content());
		commentRepository.commentUpdate(comment.getBc_content(), comment.getBc_no());
	}
	@Transactional
	public void commentDelete(int bc_no) {
		commentRepository.deleteById(bc_no);
	}
	
}
