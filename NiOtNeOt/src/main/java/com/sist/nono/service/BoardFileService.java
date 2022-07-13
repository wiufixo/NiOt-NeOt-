package com.sist.nono.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.model.BoardFile;
import com.sist.nono.repository.BoardFileRepository;

@Service
public class BoardFileService {
	
	
	@Autowired
	private BoardFileRepository repository;
	
	
//	public int insertFile(List<BoardFile> list) {
//		
//	};
//
//	public BoardFile selectAttachDetail(int bf_no) {
//		repository.findById(bf_no);
//	};
//
//	public int deleteAttach(int b_no) {
//		repository.deleteById(b_no);
//	};
//
	
	@Transactional(readOnly = true)
	public List<BoardFile> findAllByBoard(int b_no){
		int tot = repository.totalFile(b_no);
		if(tot<1) {
			return Collections.emptyList();
		}
		return repository.findAllByBoard(b_no);
	};
	
	
	@Transactional(readOnly = true)
	public BoardFile findById(int bf_no) {
		return repository.findById(bf_no).orElseThrow(()->{
			return new IllegalArgumentException("파일 로드 실패: 파일을 찾을 수 없습니다!");
			});
	}
	
	@Transactional(readOnly = true)
	public int totalFile(int b_no) {
		return repository.totalFile(b_no);
	};
	
	@Transactional
	public int deleteFile(int b_no) {
		return repository.deleteFile(b_no);
	}
	@Transactional
	public void deleteFileByNo(int b_no) {
		repository.deleteById(b_no);
	}
	
}
