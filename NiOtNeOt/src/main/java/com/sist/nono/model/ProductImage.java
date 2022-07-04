package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class ProductImage {
	
	@Id
	private int pi_no;
	private int cu_no;
	private int pr_no;
	
	private String pi_name;
	@CreationTimestamp
	private Date pi_created;
	@UpdateTimestamp
	private Date pi_updated;
}
