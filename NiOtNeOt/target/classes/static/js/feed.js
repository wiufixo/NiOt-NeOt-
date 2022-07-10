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
			location.href = `/feed/detailFeed/${f_no}`;
		}

	})

}

var fdelete = function() {
	alert("함수동작")
	let f_no = $("#f_no").text();
	console.log(f_no)
	$.ajax({
		url: "/feed/deleteSubmit/" + f_no,
		type: "DELETE",

	}).done(function(resp) {
		alert("피드 삭제를 성공하였습니다!");
		console.log(resp)
		location.href = "/feed/list";
	}).fail(function(error) {
		alert(error);
	})
}
var fcdelete = function(f_no, fc_no) {
	alert("함수 동작함")

	console.log(f_no);
	console.log(fc_no)

	$.ajax({
		url: `/feed/deleteComment/${f_no}/${fc_no}`,
		type: "POST",
		dataType: "json",
		success: function(re) {
			alert("성공")
			console.log(f_no);

			location.href = `/feed/detailFeed/${f_no}`
		}

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
		type: "POST",
		data: data,
		success: function() {
			alert("피드 수정 성공")
			//	location.href = "/feed/detailFeed/"+f_no;
		}
	});
}




var fcupdate = function(fc_no) {
	var f_no = $("#f_no").text();
	var fc_comment2 = $("#fc-comment2").val();

	console.log(f_no);
	console.log(fc_no);
	console.log(fc_comment2);
	var data = {
		fc_no: fc_no,
		fc_comment: fc_comment2
	}

	console.log(data);
	$.ajax({
		url: `/feed/updateComment/${f_no}`,
		type: "PUT",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(function(resp) {
		alert("댓글수정을 성공하였습니다!");
		location.href = `/feed/detailFeed/${f_no}`
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
}

var fcmove = function(fc_no, fc_comment) {
	alert("버튼 동작함")
	console.log(fc_no)
	console.log(fc_comment)
	$("#fc-comment2").css("display", "inline")
	$("#fc-update").css("display", "inline")
	document.getElementById("fc-comment2").value = fc_comment;
	document.getElementById("fc-update").setAttribute("onclick", "fcupdate(" + fc_no + ")");
}




$(document).ready(function() {

	let f_no = $("#f_no").text(); //피드 	번호 (f_no),div(text())
	let fc_no = $("fc_no").val(); //피드 댓글 번호 (fc_no),input(val())
	$("#fc-comment2").css("display", "none")
	$("#fc-update").css("display", "none")
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

	$("#feed-cancle").on("click", function() {
		alert("버튼 동작함")

		location.href`/feed/detailFeed/${f_no}`
	})
	//-------------------------------------------------------------------------------------------------------------
	$("#fc-save").on("click", function() {
		fcsave();
	})

	$(".fc-delete").on("click", function() {
		alert("버튼 동작함")

		fcdelete();
	})


	$(".fc-move").on("click", function() {
		alert("버튼 동작함")
		$(".fc-comment2").css("display", "inline");
		$(".fc-update").css("display", "inline");
	})

	$("#fc-update").on("click", function() {
		alert("버튼 동작함")
		fcupdate();
	})

})

