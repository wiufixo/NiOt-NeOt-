package com.sist.nono.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.Category;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductDeal;
import com.sist.nono.model.ProductImage;
import com.sist.nono.model.ProductStatus;
import com.sist.nono.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class ProductFormDTO {

	private int pr_no;
	private User user;
	private Category category;
	private String pr_name;
	private int pr_cost;
	private String pr_content;
	private MultipartFile multipartFile;
	private ProductDeal pr_deal;
	private ProductStatus pr_status;
	
	
	// 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
	private List<ProductImageDTO> productImageDTOList = new ArrayList<>();
	
	//상품 이미지 아이디를 저장하는 리스트
	//상품 등록 전에는 이미지가 없으니까 비어있음(이미지도 공백, 아이디도 공백)
	//그냥 수정할 때 이미지 아이디 저장해둘 용도
	private List<Integer> productImageNo =  new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//productFormDTO를 entity로 변환
	public Product createProduct() {
		return modelMapper.map(this,Product.class);
	}
	
	public static ProductFormDTO of(Product product) {
		return modelMapper.map(product, ProductFormDTO.class);
		//entity를 파라미터로 받아서 dto로 반환
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//productDTO의 내용을 product로 변환하는 메소드가 필요
//	public Product toProduct() {
//		Product product = new Product();
//		product.setPr_no(pr_no);
//		product.setPr_name(pr_name);
//		product.setPr_content(pr_content);
//		product.setPr_cost(pr_cost);
//		Category cg = new Category();
//		cg.setCa_no(category);
//		product.setCategory(cg);
//		if(pr_deal.equals("거래가능")) {
//			product.setPr_deal(ProductDeal.Tradeable);
//		}else{
//			product.setPr_deal(ProductDeal.Untradeable);
//		}
//		if(pr_status.equals("거래불가능")) {
//			product.setPr_status(ProductStatus.Open);
//		}else {
//			product.setPr_status(ProductStatus.Private);
//		}
//		return product;
//	}
//	
//	
//	
//	//productDTO의 내용을 ProductImage로 변환하는 메소드
//	public ProductImage toProductImage() {
//		ProductImage img = new ProductImage(); 
//		img.setPi_name(multipartFile.getOriginalFilename()+new Date().toString());
//		img.setPi_created(new Date());
//		img.setPi_originName(multipartFile.getOriginalFilename()+new Date().toString());
//		img.setPi_url("image/product");
//		
//		return img;
//	}
//	
}
