package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;

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
@Table(name="loginlist")
public class LoginList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ll_no;
	
	private int cu_no;
	
	@CreationTimestamp
	private Timestamp login_date;
	
	private String login_ip;
}
