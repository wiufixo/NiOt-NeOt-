package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	private int pr_no;
	
	//@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cu_no")
	private User user;
	
	//@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ca_no")
	private int category;
	
	private String pr_name;
	private int pr_cost;
	
	@Lob
	private String pr_content;
	
	@CreationTimestamp
	private Date pr_created;
	@UpdateTimestamp
	private Date pr_updated;
	private int pr_hit;
	private int pr_status;
	private String pr_deal;
}
