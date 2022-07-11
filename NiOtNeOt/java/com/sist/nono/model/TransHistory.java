package com.sist.nono.model;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transhistory")
public class TransHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tr_no;

	private int buy_no;
	
	@ManyToOne(fetch = FetchType.EAGER) //기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name="pr_no")
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER) //기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name="cu_no")
	private Customer customer;
	
	
	private int sell_score;
	private int buy_score;
	
	@CreationTimestamp
	private Timestamp tr_created;
}
