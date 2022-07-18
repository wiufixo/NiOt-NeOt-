package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"chat","chatroom"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chatfile") 
@Entity
public class ChatFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment
	private int cf_no;

	@ManyToOne(fetch = FetchType.EAGER) // 기본패치전략, 반드시 들고와야하는 칼럼
	@JoinColumn(name = "ch_no")
	private Chat chat; // db에서는 안되지만 orm에서는 object를 사용할수있다

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cr_no")
	@JsonIgnoreProperties({"customer","product"})
	private ChatRoom chatroom;

	private int cu_no;
	
	private String cf_name;

	@CreationTimestamp
	private Timestamp cf_created;
}
