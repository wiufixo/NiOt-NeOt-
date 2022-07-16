package com.sist.nono.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sist.nono.model.Product;
import com.sist.nono.model.ProductImage;
import com.sist.nono.repository.ProductImageRepository;

import javax.persistence.EntityNotFoundException;

// 상품 이미지를 업로드하고, 상품 이미지 정보를 저장
@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageService {

    @Value("${itemImgLocation}") // application.properties 에 적었던 itemImgLocation 값을 불러와서 itemImgLocation 변수에다가 넣어줌
    private String itemImgLocation;

    private final ProductImageRepository repository;

    private final ProductFileService fileService;

    public void saveItemImg(ProductImage productimage, MultipartFile productImgFile) throws Exception{

        // 일단 비어있는 변수 만들어두고 밑에서 넣어줌
        String pi_originName =productImgFile.getOriginalFilename();
        String pi_name = "";
        String pi_url = "";

        //파일 업로드
        if(!StringUtils.isEmpty(pi_originName)){

            // 상품 이미지 이름 = 저장할 경로 + 파일이름 + 파일크기(byte)
        	pi_name = fileService.uploadFile(itemImgLocation, pi_originName,
        			productImgFile.getBytes());

            // 저장한 상품 이미지를 불러올 경로
        	pi_url = "/product/image/" + pi_name;
        }

        //상품 이미지 정보 저장
        productimage.updateProductImage(pi_originName,pi_name,pi_url);
        repository.save(productimage);
        /*
        imgName: 실제 로컬에 저장된 상품 이미지 파일 이름
        oriImgName: 업로드했던 상품 이미지 파일 초기 이름
        imgUrl: 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러올 경로
         */
    }
    
    // 상품 이미지 수정
    public void updateProductImg(int pi_no, MultipartFile productImgFile) throws Exception{
    	if(!productImgFile.isEmpty()) {//상품 이미지를 수정한 경우, 상품 이미지를 업데이트함
    		ProductImage savedPrdouctImage = repository.findById(pi_no) // 상품 이미지 번호를 이용해서 기존에 저장했던 상품 이미지 엔티티를 조회
    				.orElseThrow(EntityNotFoundException::new);
    		
    		//기존 이미지 파일 삭제
    		if(!StringUtils.isEmpty(savedPrdouctImage.getPi_name())) {
    			fileService.deleteFile(itemImgLocation+"/"+savedPrdouctImage.getPi_name());
    		}
    		String pi_originName = productImgFile.getOriginalFilename();
    		//업데이트 성공한 이미지 파일 업로드
    		String pi_name = fileService.uploadFile(itemImgLocation, pi_originName, productImgFile.getBytes());
    		
    		//변경된 상품 이미지 정보를 세팅
    		String pi_url = "/product/image"+pi_name;
    		savedPrdouctImage.updateProductImage(pi_originName, pi_name, pi_url);
    	}

}
}
