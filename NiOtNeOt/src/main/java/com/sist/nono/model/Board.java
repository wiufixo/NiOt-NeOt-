package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"customer","boardComment","boardFile"})
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
	private Customer customer; // db에서는 안되지만 orm에서는 object를 사용할수있다
	
	//@Column(nullable = false, length = 100)
	@NotEmpty(message = "*제목이 없습니다.")
	private String b_title;
	
	@NotEmpty(message = "*내용이 없습니다.")
	@Lob //섬머노트 라이브러리로 <html>태그 섞여 디자인 될것이라 대용량데이터
	private String b_content;
	
	@CreationTimestamp
	private Timestamp b_created;
	
	private Date b_update;
	
	private int b_hit;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) //테이블의 칼럼으로 생성하지 말아주세요, 반드시 갖고와주세요
	@JsonIgnoreProperties({"board"})
	@OrderBy("bc_no desc")
	private List<BoardComment> boardComment;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true) //테이블의 칼럼으로 생성하지 말아주세요, 반드시 갖고와주세요
	@JsonIgnoreProperties({"board"})
	private List<BoardFile> boardFile;
	
	/**
     * 조회 수 증가
     */
    public void increaseHit() {
        this.b_hit++;
    }

}
