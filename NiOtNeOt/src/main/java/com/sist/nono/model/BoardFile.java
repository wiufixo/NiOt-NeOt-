package com.sist.nono.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boardFile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFile {
	
	@Id
	private int bf_no;
	
	private int b_no;
	private String bf_name;
	private Date bf_created;
	private Date bf_updated;
	
}
