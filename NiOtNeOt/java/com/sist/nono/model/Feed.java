package com.sist.nono.model;

import java.security.Timestamp;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @Getter,@Setter,@ToString,@EqualsAndHashCode 와 @RequiredArgsConstructor 를 합쳐 놓은 종합 선물세트
@Entity // DB테이블에 대응하는 하나의 클래스,JPA를 사용해서 DB 테이블과 매핑할 클래스는 꼭 @Entity를 붙여줘야한다.
@AllArgsConstructor // 클래스에 선언된 모든 iv를 매개변수 가 있는 생성자 로 만들어준다
@Builder // 생성자의 매개변수 순서와는 상관 없이 타입이 맞으면 대입
@NoArgsConstructor // 매개변수 없는 생성자
@DynamicInsert // 널 값을 제외 해주고 Insert

@Table(name = "feed")
public class Feed {
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다 ===> auto_increment (== oracle Sequence 와 같다)
	private int f_no;

	@Column(nullable = false, length = 50)
	private String f_title;
	
		
	@Column(nullable = false, length = 3000)
	private String f_content;

	@Column(updatable = false )
	@CreationTimestamp // sysdate 과 같은 뜻
	private LocalDateTime f_created = LocalDateTime.now();

	@UpdateTimestamp
	private LocalDateTime f_updated = LocalDateTime.now();

	@Column(nullable = false)
	private int f_status;

	@Column(nullable = false)
	private int f_hit;
	
	private String isFileChanged;

	
//	@Enumerated(EnumType.STRING)
//	private Public noticeYn;
//	
//	@Enumerated(EnumType.STRING)
//	private Public sercetYn;
	
	
//-----------------------------------------------------------------------------------------------------------------------
	// User Join (부모)
	@ManyToOne
	@JoinColumn(name = "cu_no", insertable = true, updatable = true)
	private User user;

	//---------------------------------------------------------------------------------------------------------------------
	//자식 , 자식 들과 많은 관계를 맺게 되면 은 jpa 에서 처리 하기 힘들기 때문에 , 데이터를 직접 받아올 한개의 클래스에게만 fetch = FetchType.EAGER 을 붙이고
	//나머지는 fetch = FetchType.LAZY 을 쓰는것이 좋다.
	
	// Comment Join
	@OneToMany(mappedBy = "feed", fetch = FetchType.EAGER,cascade = CascadeType.ALL )	
	@JsonIgnoreProperties({"feed"})
	@OrderBy("fc_no")
	private List<FeedComment> feedComment;
	
	// Img Join
	@OneToMany(mappedBy = "feed", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"feed"})
	private List<FeedImg> feedImg;
	
	// Tag Join
	@OneToMany(mappedBy = "feed", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"feed"})
	private List<FeedTag> feedTag;
	
	//Sticker Join
	@OneToMany(mappedBy = "feed",fetch =FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"feed"})
	private List<FeedSticker> feedSticker;

}
