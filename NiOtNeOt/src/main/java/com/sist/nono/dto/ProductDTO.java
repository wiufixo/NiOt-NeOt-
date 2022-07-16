package com.sist.nono.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sist.nono.model.Category;
import com.sist.nono.model.Product;
import com.sist.nono.model.ProductDeal;
import com.sist.nono.model.ProductImage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
	private int pr_no;
	
	private String ca_name;

	private String pr_image;
	
	private String pr_name;
	
	private int pr_cost;
	
	private String pr_content;

	private String pr_deal;
	
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
	}
}
