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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cu_no;

	@NotEmpty(message = "*아이디를 입력해주세요.")
	@Size(min=5, max=15, message = "*아이디는 5~15자 사이로 입력해주세요.")
	private String cu_id;
	
	@NotEmpty(message = "*비밀번호를 입력해주세요.")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,100}",
            message = "비밀번호는 8자 이상이고 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함되어야 합니다.")
	private String cu_pwd;
	
	@NotEmpty(message = "*이름을 입력해주세요.")
	@Size(min=2, max=5, message = "*이름을 2~5자 사이로 입력해주세요.")
	private String cu_name;
	
	@NotEmpty(message = "*닉네임을 입력해주세요.")
	@Size(min=2, max=9, message = "*닉네임을 2~9자 사이로 입력해주세요.")	
	private String cu_nickname;
	
	@NotEmpty(message = "*이메일을 입력해주세요.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "*이메일 형식에 맞지 않습니다.")
	private String cu_email;
	
	private String cu_birth;
	private String cu_img;
	private int cu_gender;
	
	@Min(value=80, message = "*80~230 사이로 입력해주세요.")
	@Max(value=230, message = "*80~230 사이로 입력해주세요.")
	private int cu_height;
	
	@Min(value=40, message = "*40~180 사이로 입력해주세요.")
	@Max(value=180, message = "*40~180 사이로 입력해주세요.")
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
