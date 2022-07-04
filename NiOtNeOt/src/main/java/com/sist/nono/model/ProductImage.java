package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="productimage")
public class ProductImage {
	
	@Id
	private int pi_no;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cu_no")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="pr_no")
	private Product product;
	
	private String pi_name;
	@CreationTimestamp
	private Date pi_created;
	@UpdateTimestamp
	private Date pi_updated;
}
