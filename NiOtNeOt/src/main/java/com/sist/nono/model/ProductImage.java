package com.sist.nono.model;

import java.security.Timestamp;
import java.util.Date;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"customer","product"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="productimage")
public class ProductImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 번호 생성
	private int pi_no;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pr_no")
	//@JsonBackReference	//순환참조를 방어하기위한 annotation
	private Product product; //상품번호
	
	@Column(nullable = false)
	private String pi_name; //이미지 파일명

	@CreationTimestamp
	private Date pi_created; //생성일자
	
	@UpdateTimestamp
	private Date pi_updated;
	
	private String pi_url; // 이미지 조회 경로
	
	private Boolean repImgYn; //대표 이미지 여부
	
	//ProductImageService에서 이미지 수정할 때 사용
	public void updateProductImage(String pi_name, String pi_url) {
		this.pi_name = pi_name;
		this.pi_url = pi_url;
	}
	
	
	
}