package com.sist.nono.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="productimage")
public class ProductImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //자동으로 번호 생성
	private int pi_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cu_no")
	private User user; //회원번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pr_no")
	@JsonBackReference	//순환참조를 방어하기위한 annotation
	private Product product; //상품번호
	
	@Column(unique = true)
	private String pi_name; //이미지 파일명
	
	private String pi_originName; //원본이미지 파일명
	@CreationTimestamp
	private Date pi_created; //생성일자
	private Date pi_updated; //업데이트 일자
	
	private String repimgYn; // 대표 이미지 여부
	private String pi_url; // 이미지 조회 경로
	
	
}
