package com.sist.nono.model;

import java.security.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sist.nono.dto.ProductFormDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter@Setter
@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product{
	@Id @GeneratedValue
	@Column(name="pr_no")
	private int pr_no;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ca_no")
	private Category category;
	
	//cascade: 부모가 바뀌면 자식도 바뀌고 부모랑 자식 연동
	//orphanRemoval = true로 하면 고아 객체를 지울 수 있음
	@OneToMany(mappedBy ="product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ProductImage> image = new ArrayList<>();
	
	private String pr_name;
	
	private int pr_cost;
	
	@Lob
	private String pr_content;
	
	
	@CreationTimestamp
	private Date pr_created;

	@UpdateTimestamp
	private Date pr_updated;
	
	
	@Enumerated(EnumType.STRING)
	private ProductDeal pr_deal;
	
	

	
	//업데이트
	public void updateProduct(ProductFormDTO productFormDTO) {
		this.pr_name = productFormDTO.getPr_name();
		this.category = productFormDTO.getCategory();
		this.pr_cost = productFormDTO.getPr_cost();
		//this.image = product.image;
		this.pr_content = productFormDTO.getPr_content();
		this.pr_deal = productFormDTO.getPr_deal();
		//this.pr_updated = ProductFormDTO.get;
		
				
	}
	
}
