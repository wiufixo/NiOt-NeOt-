package com.sist.nono.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.nono.model.FeedImg;
import com.sist.nono.repository.FeedImgRepository;

@Service
public class FeedFileService {
	
	@Autowired
	private FeedImgRepository fi_r;
	
	public List<FeedImg> findAllByFeed(int f_no) {
		int tot = fi_r.totalFile(f_no);
		
		if(tot<1) {
			return Collections.emptyList();
		}
		
		return fi_r.findAllByFeed(f_no);
	}
	
	public FeedImg findById(int fi_no) {
		return fi_r.findById(fi_no).orElseThrow(()->{
			return new IllegalArgumentException("파일 로드 실패: 파일을 찾을 수 없습니다!");
		});
	}
	
	public int totalFile(int f_no) {
		
		return fi_r.totalFile(f_no);
		
	};
	
	@Transactional
	public int deleteFile(int f_no) {
		
		return fi_r.deleteFile(f_no);
	}
}
