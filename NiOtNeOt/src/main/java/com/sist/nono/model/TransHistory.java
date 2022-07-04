package com.sist.nono.model;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tranhistory")
public class TransHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tr_no;

	private int buy_no;
	private int pr_no;
	private int cu_no;
	private int sell_score;
	private int buy_score;
	
	@CreationTimestamp
	private Timestamp tr_created;
}