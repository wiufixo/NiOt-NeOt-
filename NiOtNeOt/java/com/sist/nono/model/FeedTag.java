package com.sist.nono.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "feedTag")
public class FeedTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ft_no;
	
	@Column(length = 30)
	private String ft_name;
	
	//Feed Join
	@ManyToOne
	@JoinColumn(name = "f_no", insertable = true, updatable =true)
	private Feed feed;
}
