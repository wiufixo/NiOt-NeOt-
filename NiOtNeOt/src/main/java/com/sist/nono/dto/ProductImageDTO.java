package com.sist.nono.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.sist.nono.model.ProductImage;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductImageDTO {
	
	private int pi_no;
	private String pi_name;
	private String pi_originName;
	private Date pi_created;
	private Date pi_updated;
	private String pi_url;
	private String repImgYn;
	
	//멤버 변수로 modelmapper객체를 추가
	private static ModelMapper modelMapper = new ModelMapper();
	
	//productImage 엔티티 객체를 파라미터로 받아서 
	//productImage 객체의 자료형과 멤버변수의 이름이 같을 때 productImage로 값을 복사해서 반환
	public static ProductImageDTO of(ProductImage productImage) {
		return modelMapper.map(productImage, ProductImageDTO.class);
	}
	
}
