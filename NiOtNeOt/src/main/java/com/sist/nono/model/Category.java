package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import antlr.collections.List;
import lombok.Data;

@Data
public class Category {
	@Id
	private int ca_no;
	
	@Column(unique = true)
	private String ca_name;
	@CreationTimestamp
	private Date ca_created;
	@UpdateTimestamp
	private Date ca_updated;
	
	
	
}
