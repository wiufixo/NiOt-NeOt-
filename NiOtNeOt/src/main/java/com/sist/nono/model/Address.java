package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address")
public class Address {

	@Id
	private int cu_no;
	
	private String main_adr;
	private String sub_adr1;
	private String sub_adr2;
}
