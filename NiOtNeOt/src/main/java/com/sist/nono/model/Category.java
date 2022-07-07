package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import antlr.collections.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category")
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