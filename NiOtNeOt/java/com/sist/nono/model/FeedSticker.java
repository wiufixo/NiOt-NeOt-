package com.sist.nono.model;

import javax.persistence.Entity;
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

@Table(name = "feedSticker")
public class FeedSticker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fs_no;

	// FeedJoin
	@ManyToOne
	@JoinColumn(name = "f_no", insertable = true, updatable =true)
	private Feed feed;
}
