package com.sist.nono.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class ProductFileService {

	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
	
	UUID uuid = UUID.randomUUID(); //완전 랜덤
	String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	
	//uuid로 받은 값 + 원래 파일 확장자를 조합.(savedFileName:저장될 파일 이름)
	String savedFileName = uuid.toString() + extension;
	String fileUploadFullUrl = uploadPath + "/" + savedFileName;
	
	//(FileOutputStream :  출력값이 바이트)
	//생성자로 파일이 저장될 위치와, 파일의 이름을 넘겨서, 파일에 쓸 파일 출력 스트림을 만듦
	FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
	
	fos.write(fileData); //fileData를 파일 스트림에 입력함
	fos.close();
	
	return savedFileName; //파일 이름만 반환
}

	public void deleteFile(String filePath) throws Exception{
		File deleteFile = new  File(filePath); //파일 저장 경로를 이용해서 파일 객체 생성
		
		if(deleteFile.exists()) {	// 파일이 존재하면 삭제
			deleteFile.delete();
			log.info("삭제하였습니다");
		}else {
			log.info("존재하지 않습니다");
		}
	}
}