package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "boardFile")
@Entity
public class BoardFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bf_no;
	
	@ManyToOne
	@JoinColumn(name="b_no")
	@JsonIgnoreProperties({"boardFile"})
	private Board board;
	
	private String original_name;
	
	private String save_name;
	
	private int size;
	
	@CreationTimestamp
	private Timestamp bf_created;
	
}
