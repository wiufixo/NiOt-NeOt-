package com.sist.nono.service;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.dto.FeedFileUpload;
import com.sist.nono.model.BoardFile;
import com.sist.nono.model.Feed;
import com.sist.nono.model.FeedComment;
import com.sist.nono.model.FeedImg;

import com.sist.nono.repository.FeedCommentRepository;
import com.sist.nono.repository.FeedImgRepository;
import com.sist.nono.repository.FeedLikeRepository;
import com.sist.nono.repository.FeedRepository;
import com.sist.nono.repository.FeedStickerRepository;
import com.sist.nono.repository.FeedTagRepository;

import lombok.experimental.PackagePrivate;

@Service
public class FeedService {

	@Autowired
	private FeedRepository fr;

	@Autowired
	private FeedCommentRepository fcr;

	@Autowired
	private FeedImgRepository fir;

	@Autowired
	private FeedTagRepository ftr;

	@Autowired
	private FeedStickerRepository fsr;

	@Autowired
	private FeedLikeRepository flr;
	
	@Autowired
	private FeedFileUpload fileUpload;

	@Transactional
//	 @Transactional 클래스나 메서드에 붙여줄 경우 해당 범위 내 메서드가 트랜잭션이 되도록 보장
//	// 선언적 트랜잭션이라고도 함, 직접 객체를 만들 필요 없이 선언만으로 관리를 용이
//	// 해당 메서드를 실행하는 도중 메서드 값을 수정/삭제 하려는 시도가 들어와도 값의 신뢰성 보장
//	// 실행 도중 오류가 발생해도 rollback 해서 DB에 해당 결과가 반영되지 않도록 할 수 있다.
//	
	// Feed 모든 리스트 찾기
	public List<Feed> findAllFeed() {
		// JpaRepository 클래스를 통해 기본적인 CRUD 및 페이징 처리 를 해줌
		return fr.findAll();
	}

	// Feed 해당 게시물 정보 가져오기
	@Transactional
	public Feed findByIdFeed(int f_no) {

		return fr.findById(f_no).orElseThrow(() -> {
			return new IllegalArgumentException("피드 상세보기 실패: 피드를 찾을 수 없습니다!");
		});
	}

	// Feed 등록
	@Transactional
	public Feed insertFeed(Feed feed) {
		feed.setF_hit(0);
		// feed.setUser(user);

		return	fr.save(feed);

	}
	
	public void insertFeed(Feed feed,List<MultipartFile> files) {
		
		Feed feed2 = insertFeed(feed);
		
		if (feed2 != null) {
			List<FeedImg> fileList = fileUpload.uploadFiles(files, feed2.getF_no());
			System.out.println(fileList);
			if(CollectionUtils.isEmpty(fileList) == false) {
				for(FeedImg fi:fileList) {
					fir.save(fi);
					System.out.println("fi="+fi);
				}
			}
			
		}
		fr.save(feed2);
	}

	// Feed 수정
	public void updateFeed(Feed f) {
		
		System.out.println("서비스에 도착함");
		
		
		fr.save(f);

		
	}
	
	
	@Transactional
	public void updateFeed(Feed f,List<MultipartFile> files) {
			

		
		fr.save(f);
		
		System.out.println("현재 피드의 번호1 : " + f.getF_no());
		
	//	String isFileChanged = f.getIsFileChanged();
		
	//	if(isFileChanged.equals("Y")) {
	//		System.out.println("현재 피드의 번호2 : " + f.getF_no());
			
	//		fir.deleteFile(f.getF_no());
	//	}
		
		System.out.println("현재 피드의 번호 3: " + f.getF_no());
		
		List<FeedImg> fileList = fileUpload.uploadFiles(files, f.getF_no());
		if (CollectionUtils.isEmpty(fileList) == false) {
		//	board.setBoardFile(fileList);
			for(FeedImg fi : fileList) {
				fir.save(fi);
				System.out.println("bf="+fi);
			}
		}
		
		
		
		
		
	}

	// Feed 삭제
	@Transactional
	public void deleteFeed(int f_no) {
		fr.deleteById(f_no);
	}
	
	
	//해당 Feed 조회수 증가
	@Transactional
	public void increaseHit(int f_no) {
		fr.increaseHit(f_no);
	}
	
//------------------------------------------------------------------------------------------	
	// Feed 댓글 등록
	public void insertFeedComment(int f_no, FeedComment f_Comment) {
		int fc_ref = 0;

//		f_Comment.setUser(user);
		f_Comment.setFeed(fr.findById(f_no).orElseThrow(() -> {
			return new IllegalArgumentException("댓글의 게시글 찾기 실패!");
		}));
		f_Comment.setFc_step(0);
		f_Comment.setFc_level(0);
		fc_ref = fcr.save(f_Comment).getFc_no();
		f_Comment.setFc_ref(fc_ref);

		fcr.save(f_Comment);

	}

	// Feed 댓글 수정
	public void updateFeedComment(FeedComment fc) {
	
		fcr.save(fc);
	}

	// Feed 댓글 삭제

	public void deleteFeedComment(int fc_no) {

		System.out.println("컨트롤러에서 데이터 전달됨");
		System.out.println("fc_no:"+fc_no);
		
		
	 
		fcr.deleteById(fc_no);
	}
	
	// 해당 Feed 댓글 가져오기
	public FeedComment findByIdFeedComment(int fc_no) {
		
		 return fcr.findById(fc_no).orElseThrow(() -> {
			return new IllegalArgumentException("댓글의 게시글 찾기 실패!");
		});
	}
}
