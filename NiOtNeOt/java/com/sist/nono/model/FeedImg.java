package com.sist.nono.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
	
	private String original_name;
	
	private String save_name;
	
	private int size;
	
	@Column(updatable = false )
	@CreationTimestamp // sysdate 과 같은 뜻
	private LocalDateTime fi_created = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime fi_updated = LocalDateTime.now();
	
//--------------------------------------------------------	
	//Feed Join
	@ManyToOne
	@JoinColumn(name = "f_no", insertable = true, updatable =true)
	private Feed feed;
	
	

}
