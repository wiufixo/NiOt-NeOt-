package com.sist.nono.dto;

import com.sist.nono.model.ProductImage;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductImageDTO {
	
	private int pi_no;
	private String pi_name;

	private String pi_url;
	private Boolean repImgYn;
	
	public ProductImageDTO(ProductImage image) {
		this.pi_no = image.getPi_no();
		this.pi_name = image.getPi_name();
		this.pi_url = image.getPi_url();
		this.repImgYn = image.getRepImgYn();
	}
	
}
