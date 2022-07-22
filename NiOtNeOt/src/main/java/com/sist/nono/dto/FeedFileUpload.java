package com.sist.nono.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.BoardFile;
import com.sist.nono.model.FeedImg;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.FeedRepository;


@Component
//@Bean 과 달리 개발자가 직접 작성한 클래스를 스프링 컨테이너에 등록하는 데 사용
public class FeedFileUpload {
	
	@Autowired
	private FeedRepository feedRepository;
	
	/** 오늘 날짜 */
//	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

	/** 업로드 경로 */
	
	private final String uploadPath = Paths.get("C:", "develop", "upload").toString();

	/**
	 * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	 * @return 랜덤 문자열
	 */
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
	 * @param files    - 파일 Array
	 * @param f_no - 게시글 번호
	 * @return 업로드 파일 목록
	 */
	public List<FeedImg> uploadFiles(List<MultipartFile> files, int f_no) {

		/* 파일이 비어있으면 비어있는 리스트 반환 */
		if (files.get(0).getSize() < 1) {
			return Collections.emptyList();
		}

		/* 업로드 파일 정보를 담을 비어있는 리스트 */
		List<FeedImg> fileList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File dir = new File(uploadPath);
		/*"C:/develop/upload/today(오늘날짜) 와 같은 패턴의 디렉토리(폴더) 생성*/ 
		if (dir.exists() == false) {
			/* exists : 경로가 존재하는지 확인 한 후 없으면(false) 해당경로에 만들어준다.*/
			dir.mkdirs();
			/* mkdir() : 한번에 하나의 디렉토리만 생성*/
			/* mkdirs() : 한번에 여러개의 디렉토리 생성*/
		}

		/* 파일 개수만큼 forEach 실행 */
		for (MultipartFile file : files) {
			/*if (file.getSize() < 1) {
				continue;
			}*/
			
			try {
				/* 파일 확장자 */
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				/* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
				final String saveName = getRandomString() + "." + extension;

				/* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
				File target = new File(uploadPath, saveName);
				file.transferTo(target);

				/* 파일 정보 저장 */
				FeedImg feedImg = new FeedImg();
				feedImg.setFeed(feedRepository.findById(f_no).orElseThrow(()->{
					return new IllegalArgumentException("피드 찾기 실패: 피드 번호를 찾을 수 없습니다!");
				}));
				feedImg.setOriginal_name(file.getOriginalFilename());
				feedImg.setSave_name(saveName);
				feedImg.setSize((int)file.getSize());
				/* 파일 정보 추가 */
				fileList.add(feedImg);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} // end of for

		return fileList;
	}
 
}
