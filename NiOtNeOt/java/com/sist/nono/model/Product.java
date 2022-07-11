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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id @GeneratedValue
	@Column(name="pr_no")
	private int pr_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cu_no")
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ca_no")
	private Category category;
	
	@OneToMany(mappedBy ="product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImage> image = new ArrayList<ProductImage>();
	
	private String pr_name;
	
	private int pr_cost;
	
	@Lob
	private String pr_content;
	
	@CreationTimestamp
	private Date pr_created;

	private Date pr_updated;
	
	//조회수
	private int pr_hit;
	
	@Enumerated(EnumType.STRING)
	private ProductStatus pr_status;
	
	
	@Enumerated(EnumType.STRING)
	private ProductDeal pr_deal;
	
	
	//조회수
	
	public void addHit(int hit) {
		this.pr_hit += hit;
	}
	
}
