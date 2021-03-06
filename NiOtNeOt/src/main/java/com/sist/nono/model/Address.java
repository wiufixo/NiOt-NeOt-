package com.sist.nono.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
}
