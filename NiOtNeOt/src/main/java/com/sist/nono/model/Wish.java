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
@Table(name = "wish")
public class Wish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ws_no;

	private int cu_no;
	private int pr_no;
	
	@CreationTimestamp
	private Timestamp ws_created;
	
}
