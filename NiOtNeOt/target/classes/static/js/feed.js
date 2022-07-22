let fileList = [];
//컨트롤러에서 전달받은 fileList 객체를 의미.
//fileIdx를 처리 하기 위해 사용
//let fileIdx = 0
let fileIdx = isEmpty(fileList) ? 0 : fileList.length; /*[- 파일 인덱스 처리용 전역 변수 -]*/
//기존에는 해당 변수의 값을 0 으로 초기화 
//파일이 포함된 게시글 수정의 경우에는 각 영역의 <label>태그와 <input>태그가 
//file_${fileIdx} 에 해당하는 id를 가지고 있기 때문에 인덱스 증가 처리를 위해서는
//fileList의 크기 (length) 를 기준으로 초기화 되어야함
//file_0,file_1,file_2



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
	//<div> 태그의 data-name이 fileDiv 인것을 찾아와 변수에 담는다
	if (fileDivs.length > 2) { //fileDivs
		alert('파일은 최대 세 개까지 업로드 할 수 있습니다.');
		return false;
	}
	console.log("******filechanged**********")
	console.log($("#isFileChanged").val());
	//파일이 추가 되는 시점에 앞에서 hidden 타입으로 추가한 changeYn의 값을 ="Y" 값으로 변경
	$("#isFileChanged").val("Y"); // Y값으로 변경
	console.log($("#isFileChanged").val());

	fileIdx++; // 파일 추가 

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
			window.location.reload();
			//location.href = `/feed/detailFeed/${f_no}`
		}

	})
}


var fupdate = function() {

	var f_no = $("#f_no").val();
	
	var isFileChanged=$("#isFileChanged").val();
	
	var formData = new FormData();
	//form 태그를 만들어 넘김 데이를 담아준다.
	
	var data = {
		f_no: f_no,
		f_title: $("#title").val(),
		f_content: $("#content").val(),
		isFileChanged:isFileChanged
	}

	console.log(f_no)
	console.log(data);
	
	var fileInput=$('.files');
	
	for (var i = 0; i < fileInput.length; i++) {
			if (fileInput[i].files.length > 0) {
				for (var j = 0; j < fileInput[i].files.length; j++) {
					console.log(" fileInput[i].files[j] :::"+ fileInput[i].files[j]);
					
					// formData에 'file'이라는 키값으로 fileInput 값을 append 시킨다.  
					formData.append('files', $('.files')[i].files[j]);
				}
			}
		}
		
		formData.append('key', new Blob([ JSON.stringify(data) ], {type : "application/json"}));

	$.ajax({
		url: `/feed/updateSubmit/${f_no}`,
		type: "POST",
		enctype: 'multipart/form-data',        
		data: formData,  
		processData: false,        
		contentType: false ,
		success: function(resp) {
				console.log("resp:" + resp);
	
			alert("피드 수정 성공")
				location.href = `/feed/detailFeed/${f_no}`
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

	//피드 	번호 (f_no),div(text())
	let fc_no = $("fc_no").val(); //피드 댓글 번호 (fc_no),input(val())

	//------------------------------------------------------------------------
	$("#feed-save").on("click", function() {

		fsave();

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
	
	//--------------------------------------------------------------------------------------------------------
	$("#feed-list").on("click",function(){
		console.log("fileList 만드는 함수 ")
		var f_no=$("#f_no").val();
		$.ajax({
			url:"/imgList",
			data:{f_no:f_no},
			dataType:"json",
			success:function(re){
				fileList=[];
				console.log(re)
				$(re).each(function(res){
					console.log(res)
					fileList.push(this);
				})
			}
			
		})
	})

})
