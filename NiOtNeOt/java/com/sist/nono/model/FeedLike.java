package com.sist.nono.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "feedLike")
public class FeedLike {

	@EmbeddedId
	private FeedLike_View fl_id;
	
	
}
