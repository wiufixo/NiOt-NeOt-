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
	private int main_adr_no;
	private String main_adr_detail;
	
	private String sub_adr1;
	private int sub_adr1_no;
	private String sub_adr1_detail;
	
	private String sub_adr2;
	private int sub_adr2_no;
	private String sub_adr2_detail;
}
