package com.sist.nono.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	private int b_no;
	private int cu_no;
	private String cu_id;
	private String b_title;
	private String b_content;
	private Date b_created;
	private Date b_update;
	private int b_hit;
	private int bc_no;
	private String bc_content;
}
