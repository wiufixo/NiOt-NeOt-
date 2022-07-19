package com.sist.nono.model;


import java.sql.Date;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transhistory")
public class TransHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tr_no;

	
	private int pr_no;
	private int sell_no;
	private int buy_no;
	
	//판매자에 대한 평가점수
	private int sell_score;
	
	//구매자에 대한 평가점수
	private int buy_score;
	
	@CreationTimestamp
	private Timestamp tr_created;
}
