package com.sist.nono.controller.api;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.nono.dto.ResponseDTO;
import com.sist.nono.model.BoardComment;
import com.sist.nono.model.Feed;
import com.sist.nono.model.FeedComment;
import com.sist.nono.service.FeedService;

import lombok.Setter;

/*
 * var data = {
		f_no:f_no,
		f_title:$("#title").val(),
		f_content:$("#content").val(),
	}
 * 
 */

@RestController
@Setter
public class FeedAPIController {
		
	@Autowired
	private FeedService fs;
	
//----------------------------------------------------------------------------------------------------------------
	//Feed 관련
	
	@PostMapping(value = "/feed/insertSubmit")
	public ResponseDTO<Integer> insertSubmit(@RequestBody Feed f)
	{
		fs.insertFeed(f);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/feed/deleteSubmit/{f_no}")
	public ResponseDTO<Integer> delete(@PathVariable int f_no) {
		fs.deleteFeed(f_no);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/feed/updateSubmit")
	public ResponseDTO<Integer> update(Feed f) {
	 
		fs.updateFeed(f);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
//--------------------------------------------------------------------------------------------------------------------
	//피드 댓글 
	
	
	@PostMapping(value = "/feed/detailFeed/{f_no}/comment")
	public ResponseDTO<Integer> insertComment(@PathVariable int f_no,@RequestBody FeedComment fc) {
		
		System.out.println("데이터 받기");
		
		System.out.println("f_no : " + f_no + "fc : " + fc);
		
		fs.insertFeedComment(f_no, fc);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}

	
	
	@PostMapping(value = "/feed/deleteComment/{f_no}/{fc_no}") 
	public ResponseDTO<Integer> deleteComment(@PathVariable int fc_no) {
		System.out.println("데이터 받기");
		System.out.println("fc_no : " + fc_no);
		
		fs.deleteFeedComment(fc_no);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping(value = "/feed/updateComment/{f_no}") 
	public ResponseDTO<Integer> updateComment(@PathVariable int f_no, @RequestBody FeedComment fc){
		System.out.println("데이터 전달 됨");
		System.out.println(fc);
		fs.updateFeedComment(fc);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	
	}
	/*
	 * @PutMapping("/api/board/{b_no}/comment") public ResponseDTO<Integer>
	 * commentUpdate(@PathVariable int b_no, @RequestBody BoardComment comment) {
	 * service.commentUpdate(comment.getBc_no(), comment); return new
	 * ResponseDTO<Integer>(HttpStatus.OK.value(),1); }
	 */
 	
}
