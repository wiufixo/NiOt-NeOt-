package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.Board;
import com.sist.nono.model.RoleType;
import com.sist.nono.model.User;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository repository;
	
	@Transactional
	public void save(Board board, User user) {
		board.setB_hit(0);
		board.setB_ref(board.getB_no());
		board.setB_step(0);
		board.setB_level(0);
		board.setUser(user);
		Board board2 = repository.save(board);
		board.setB_ref(board2.getB_no());
		repository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> findAll(Pageable pageable) {
		return repository.findAll(pageable);
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
	public void update(int b_no, Board r_board) {
		Board board = repository.findById(b_no)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		board.setB_title(r_board.getB_title());
		board.setB_content(r_board.getB_content()); 
	} // 메소드가 끝날때 트랜잭션 종료. 영속화 되어있는 정보와 db정보를 비교하여(더티체킹) 따로 save() 호출안해도 자동 업데이트 하기위해 flush한다
	
}
