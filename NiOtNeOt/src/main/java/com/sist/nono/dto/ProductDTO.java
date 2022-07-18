package com.sist.nono.dto;

import com.sist.nono.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductDTO {
	private int pr_no;
	
	private String ca_name;

	private String pr_image;
	
	private String pr_name;
	
	private int pr_cost;
	
	private String pr_content;

	private String pr_deal;
	
	private int cu_no;
	
	public ProductDTO(Product p) {
		this.pr_no = p.getPr_no();
		this.ca_name = p.getCategory().getCa_name();
		this.pr_image = p.getImage()
				.stream()
				.filter(image -> image.getRepImgYn() == true)
				.findFirst().get().getPi_url();
		this.pr_name = p.getPr_name();
		this.pr_cost = p.getPr_cost();
		this.pr_content = p.getPr_content();
		this.pr_deal = p.getPr_deal().name();
		this.cu_no = p.getCu_no();
	}
}
