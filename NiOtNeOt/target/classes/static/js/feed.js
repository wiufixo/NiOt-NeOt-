var fsave = function() {
	var data = {
		f_title: $("#title").val(),
		f_content: $("#content").val(),
	}

	$.ajax({ //비동기 호출
		url: "/feed/insertSubmit",
		type: "POST",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"

	}).done(function(resp) {
		alert("피드 작성 성공")
		console.log(resp)
		location.href = "/feed/list";
	}).fail(function(error) {
		alert(error);
	});
}

var fcsave = function() {
	var f_no = $("#f_no").text();
	var data = {
		fc_comment: $("#fc_content").val()
	}
	console.log(f_no)
	console.log(data)

	$.ajax({
		url: `/feed/detailFeed/${f_no}/comment`,
		// == "/feed/deleteSubmit/"+f_no+/comment
		//``
		type: "POST",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		success: function(re) {
			alert("댓글 등록 성공")
			console.log(re);
			location.href = `/feed/detailFeed/${f_no}` ;
		}

	})

}

var fdelete = function() {
	alert("함수동작")
	let f_no = $("#f_no").text();
	console.log(f_no)
	$.ajax({
		url: "/feed/deleteSubmit/"+f_no,
		type: "DELETE",

	}).done(function(resp) {
		alert("피드 삭제를 성공하였습니다!");
		console.log(resp)
		location.href = "/feed/list";
	}).fail(function(error) {
		alert(error);
	})
}
var fcdelete = function() {
	alert("함수 동작함")
	
	var f_no = $("#f_no").text();
	var fc_no = $("#fc_no").val();
	console.log(fc_no)
	
	$.ajax({
		url: `/feed/deleteComment/${fc_no}`,
		type: "DELETE",
		
	}).done(function(resp) {
		console.log(resp)
		alert("댓글 삭제 성공하였습니다!");
		location.href = `/feed/detailFeed/${f_no}`
		
	}).fail(function(error) {
		alert(error);
	})
}

var fupdate = function() {
	
	var f_no = $("#f_no").val();
	var data = {
		f_no: f_no,
		f_title: $("#title").val(),
		f_content: $("#content").val(),
	}
	
	console.log(f_no)
	console.log(data);

	$.ajax({
		url: "/feed/updateSubmit",
		type:"POST",
		data: data,
		success: function() {
			alert("피드 수정 성공")
			location.href = "/feed/detailFeed/"+f_no;
		}
	});
}

var fc_move = function() {
	var fc_no = $("#fc_no").val();
	var fc_commnet = $("#fc_comment").text();
	
	var html ="";
	html += '<div>'
	
	html += '</div>'
	
	
	//<input type="text" id="fc_no" name="fc_no" th:name="fc_no" th:value="${fc.fc_no}">
	//<textarea id="fc_comment2" th:value=${fc.fc_comment} th:text=${fc.fc_comment} rows="3"></textarea>

	console.log(fc_no)
	console.log(fc_commnet);
	
}


var fcupdate = function() {
	var f_no = $("#f_no").text();
	var fc_no = $("#fc_no").val();
	var data = {
		fc_no:fc_no,
		fc_comment: $("#fc_content").val()
	} 
	$.ajax({
		url:"/feed/updateComment",
		type:"POST",
		data : data,
		success:function() {
			alert("피드 수정 성공")
			location.href = `/feed/detailFeed/${f_no}`
		}
		
	})
}





$(document).ready(function() {

	let f_no = $("#f_no").text(); //피드 	번호 (f_no),div(text())
	let fc_no = $("fc_no").val(); //피드 댓글 번호 (fc_no),input(val())
//------------------------------------------------------------------------
	$("#feed-save").on("click", function() {

		fsave();

	})
	$("#feed-delete").on("click", function() {
		console.log("버튼 작동")
		fdelete();
	})
	

	$("#feed-update").on("click", function() {

		fupdate();

	})
	
	$("#feed-cancle").on("click",function() {
		alert("버튼 동작함")
	
		location.href `/feed/detailFeed/${f_no}`
	})
//-------------------------------------------------------------------------------------------------------------
	$("#fc-save").on("click", function() {
		fcsave();
	})
	
	$("#fc-delete").on("click",function() {
		alert("버튼 동작함")
		
		fcdelete();
	})
	
	
	$("#fc_move").on("click",".commentMod",function() {
		alert("버튼 동작함")
		fc_move();
	})
 
})

