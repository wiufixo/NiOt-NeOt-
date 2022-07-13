let fileIdx=0;

var fsave = function() {
	
	
	var formData = new FormData();
	//넘길 데이터를 담아준다.
	
	var data = {
		f_title: $("#title").val(),
		f_content: $("#content").val(),
	}
	

	
		// input class 값 
		var fileInput = $('.files');
		console.log(fileInput)
		// fileInput 개수를 구한다.
		for (var i = 0; i < fileInput.length; i++) {
			if (fileInput[i].files.length > 0) {
				console.log(fileInput[i])
				for (var j = 0; j < fileInput[i].files.length; j++) {
			
					
					console.log(fileInput[i].files[j])
					console.log(fileInput[i].files.length)
					
					//console.log(" fileInput[i].files[j] :::"+ fileInput[i].files[j]);
					
					// formData에 'file'이라는 키값으로 fileInput 값을 append 시킨다.  
					formData.append('files', $('.files')[i].files[j]);
					console.log('files');
				
				}
			}
		}

		// 'key'라는 이름으로 위에서 담은 data를 formData에 append한다. type은 json  
		
	formData.append('key', new Blob([ JSON.stringify(data) ], {type : "application/json"}));

	
	$.ajax({ //비동기 호출
		url: "/feed/insertSubmit",
		type: "POST",
		enctype: 'multipart/form-data',
		data: formData,
		processData: false,        
		contentType: false

	}).done(function(resp) {
		alert("피드 작성 성공")
		console.log(resp)
		location.href = "/feed/list";
	}).fail(function(error) {
		alert(error);
	});
}

var addFile=function(){
	const fileDivs = $('div[data-name="fileDiv"]');
	if (fileDivs.length > 2) {
		alert('파일은 최대 세 개까지 업로드 할 수 있습니다.');
		return false;
	}
	console.log("******filechanged**********")
	console.log($("#isFileChanged").val());
	$("#isFileChanged").val("Y");
	console.log($("#isFileChanged").val());

	fileIdx++;

	const fileHtml = `
		<div data-name="fileDiv" class="form-group filebox bs3-primary">
			<label for="file_${fileIdx}" class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<input type="text" class="upload-name" value="파일 찾기" readonly />
				<label for="file_${fileIdx}" class="control-label">찾아보기</label>
				<input type="file" name="files" id="file_${fileIdx}" class="upload-hidden files" onchange="changeFilename(this)" style="display:none;" />
				
				<button type="button" onclick="addFile()" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
					<i class="fa fa-plus" aria-hidden="true"></i>
				</button>
				<button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
					<i class="fa fa-minus" aria-hidden="true"></i>
				</button>
			</div>
		</div>
	`;

	$('#myspan').before(fileHtml);

}

var removeFile=function(elem) {
	
	$("#isFileChanged").val("Y");
	
	const prevTag = $(elem).prev().prop('tagName');
	if (prevTag === 'BUTTON') {
		const file = $(elem).prevAll('input[type="file"]');
		const filename = $(elem).prevAll('input[type="text"]');
		file.val('');
		filename.val('파일 찾기');
		return false;
	}

	const target = $(elem).parents('div[data-name="fileDiv"]');
	target.remove();
	
	
}

var changeFilename=function(file) {
	$("#isFileChanged").val("Y");
	
	const filename = $(file)[0].files[0].name;
	const target = $(file).prevAll('input.upload-name');
	target.val(filename);
	$(file).prevAll('input[name="fileIdxs"]').remove();
	
	
}


var fcsave = function() {
	var f_no = $("#f_no").val();
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

let fdelete=function(f_no) {
	alert("함수동작")
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
		
let fcdelete = function(f_no, fc_no) {
	alert("함수 동작함")

	console.log(f_no);
	console.log(fc_no)

	$.ajax({
		url: `/feed/deleteComment/${f_no}/${fc_no}`,
		type: "DELETE",
		dataType: "json",
		success: function(re) {
			alert("성공")
			console.log(f_no);

			//location.href = `/feed/detailFeed/${f_no}`
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
				location.href = "/feed/detailFeed/"+f_no;
		}
	});
}







let openModal=function(fc_no,fc_comment) {
	alert("모달 동작함")
	$("#commentModal").modal("toggle");
	
	document.getElementById("modalContent").value = fc_comment;
	
	document.getElementById("btnCommentUpdate").setAttribute("onclick","fcupdate("+fc_no+")");
}

var fcupdate = function(fc_no) {
	var f_no = $("#f_no").val();


	console.log(f_no);
	console.log(fc_no);

	var data = {
		f_no:f_no,
		fc_no: fc_no,
		fc_comment: $("#modalContent").val()
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


	
	
	
	

	
	
	
//-----------------------------------------------------------------------------------------------------------	
$(document).ready(function() {

	let f_no = $("#f_no").text(); //피드 	번호 (f_no),div(text())
	let fc_no = $("fc_no").val(); //피드 댓글 번호 (fc_no),input(val())

	//------------------------------------------------------------------------
	$("#feed-save").on("click", function() {

		fsave();

	})
//	$("#feed-delete").on("click", function() {
//		console.log("버튼 작동")
//		fdelete();
//	})


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
