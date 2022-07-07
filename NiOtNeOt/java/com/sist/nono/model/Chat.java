package com.sist.nono.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat") 
@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment
	private int ch_no;

	@ManyToOne(fetch = FetchType.EAGER) // 기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name = "cu_no")
	private User user; // db에서는 안되지만 orm에서는 object를 사용할수있다

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cr_no")
	private ChatRoom chatroom;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pr_no")
	private Product product;

	private int bcu_no;
	
	@ColumnDefault("0")
	private int ch_checked;

	@Lob // 섬머노트 라이브러리로 <html>태그 섞여 디자인 될것이라 대용량데이터
	private String ch_content;

	@CreationTimestamp
	private Timestamp ch_created;

}
