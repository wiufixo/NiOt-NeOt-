package com.sist.nono.dto;

import java.util.Date;

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
	

	
}
