package com.sist.nono.model;



import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Getter,@Setter,@ToString,@EqualsAndHashCode 와 @RequiredArgsConstructor 를 합쳐 놓은 종합 선물세트
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@EntityListeners(value = {AuditingEntityListener.class})
@Table (name = "feedComment")
public class FeedComment {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fc_no;
	//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment (== oracle Sequence 와 같다)
	
	@Column(nullable = false, length = 3000)
	private String fc_comment;
	
	@Column(nullable =  false)
	private int fc_ref;
	
	@ColumnDefault("0")
	private int fc_step;
	
	@ColumnDefault("0")
	private int fc_level;
	
//	@Column(updatable = false)
//	@CreationTimestamp
//	private LocalDateTime fc_created = LocalDateTime.now();
//	
//	@UpdateTimestamp
//	private LocalDateTime fc_updated = LocalDateTime.now();

	
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime fc_created = LocalDateTime.now();
	
	@LastModifiedDate
	private LocalDateTime fc_updated = LocalDateTime.now();
	
	
	//User Join
	@ManyToOne
	@JoinColumn(name = "cu_no", insertable = true, updatable =true)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "f_no", insertable = true, updatable =true)
	private Feed feed;

}
