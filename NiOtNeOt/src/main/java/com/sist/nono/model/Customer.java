package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cu_no;
	
	private String cu_id;
	private String cu_pwd;
	private String cu_name;
	private String cu_nickname;
	private String cu_email;
	private String cu_birth;
	private String cu_img;
	
	private int cu_gender;
	private int cu_height;
	private int cu_weight;
	private int privacy_agree;
	
	@CreationTimestamp
	private Timestamp cu_created;
	
	private Date cu_deleted;
	
	private int unchecked_alert;
	private int unchecked_chat;
	
	@Enumerated(EnumType.STRING) //db에는 enumtype 자료형이 없으므로 string으로 변환해준다
	@ColumnDefault("'USER'")
	private RoleType role;
}
