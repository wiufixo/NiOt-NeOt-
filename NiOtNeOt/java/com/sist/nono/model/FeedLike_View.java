package com.sist.nono.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class FeedLike_View implements Serializable {
	
	@Column(name = "f_no")
	private int f_no;
	
	@Column(name = "cu_no")
	private int cu_no;
}
