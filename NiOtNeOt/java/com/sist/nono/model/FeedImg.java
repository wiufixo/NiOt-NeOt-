package com.sist.nono.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "feedImg")
public class FeedImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fi_no;
	
	@Column(nullable = false, length = 30 )
	private String fi_name;
	
	@Transient 
	//컬럼 생성 x
	private MultipartFile uploadFile;
	
//--------------------------------------------------------	
	//Feed Join
	@ManyToOne
	@JoinColumn(name = "f_no", insertable = true, updatable =true)
	private Feed feed;
	
	

}
