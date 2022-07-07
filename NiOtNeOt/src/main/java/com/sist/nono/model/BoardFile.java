package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boardFile")
@Entity
public class BoardFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bf_no;
	
	@ManyToOne
	@JoinColumn(name="b_no")
	private Board board;
	
	private String original_name;
	
	private String save_name;
	
	private int size;
	
	@CreationTimestamp
	private Timestamp bf_created;
	
	private Date bf_updated;
	
}
