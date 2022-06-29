package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@DynamicInsert ===> insert시에 null값의 칼럼은 제외하고 insert해준다 ===> enum을 활용하여 더 용이하게 프로그래밍한다
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment
	private int cu_no;
	
	@Column(nullable = false, length = 30)
	private String cu_id;
	
	@Column(nullable = false, length = 100)
	private String cu_pwd;
	
	@Column(nullable = false, length = 50)
	private String cu_email;
	
//	@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING) //db에는 enumtype 자료형이 없으므로 string으로 변환해준다
	private RoleType role;
	
	@CreationTimestamp //비워놔도 자동으로 등록한 현재시간을 입력해준다
	private Timestamp cu_created;
}
