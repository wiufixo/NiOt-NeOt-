package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "boardComment")
@Entity
public class BoardComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bc_no;
	
	@ManyToOne
	@JoinColumn(name = "cu_no")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "b_no")
	private Board board;
	
	@Column(nullable = false, length = 200)
	private String bc_content;
	
	@Column(nullable = false)
	private int bc_ref;
	
	@ColumnDefault("0")
	private int bc_step;
	
	@ColumnDefault("0")
	private int bc_level;
	
	@CreationTimestamp
	private Timestamp bc_created;
	
	private Date bc_updated;
	
}
