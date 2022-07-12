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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
	
	@Override
	public String toString() {
		return "bf_no:"+bf_no+" / original_name:"+original_name+" / save_name:"+save_name+" / size:"+size+" / bf_created:"+bf_created;
	}
}
