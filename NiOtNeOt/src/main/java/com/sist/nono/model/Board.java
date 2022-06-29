package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "board")
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment
	private int b_no;
	
	@ManyToOne(fetch = FetchType.EAGER) //기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name="cu_no")
	private User user; // db에서는 안되지만 orm에서는 object를 사용할수있다
	
	@Column(nullable = false, length = 100)
	private String b_title;
	
	@Lob //섬머노트 라이브러리로 <html>태그 섞여 디자인 될것이라 대용량데이터
	private String b_content;
	
	private int b_ref;
	
	private int b_step;
	
	private int b_level;
	
	@CreationTimestamp
	private Timestamp b_created;
	
	private Date b_update;
	
	private int b_hit;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //테이블의 칼럼으로 생성하지 말아주세요, 반드시 갖고와주세요
	private List<BoardComment> boardComment;
	
}
