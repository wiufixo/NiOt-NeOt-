
//const fileList = /*[[ ${fileList} ]]*/; /*[- 업로드 파일 리스트 -]*/

let fileList = [];

let fileIdx = isEmpty(fileList) ? 0 : fileList.length; /*[- 파일 인덱스 처리용 전역 변수 -]*/
//let fileIdx = 0; /*[- 파일 인덱스 처리용 전역 변수 -]*/

$("#btn-updateForm").on("click",function(){
	console.log("fileList만드는 함수작용!!")
	let b_no = $("#no").val();
		$.ajax({
			url:"/fileList",
			data:{b_no:b_no},
			dataType:"json",
			success:function(re){
				fileList=[];
				$(re).each(function(){
					fileList.push(this);
				})
			}
		})
});

addFile=function(){

	const fileDivs = $('div[data-name="fileDiv"]');
	if (fileDivs.length > 2) {
		alert('파일은 최대 세 개까지 업로드 할 수 있습니다.');
		return false;
	}
	console.log("*****filechanged********")
	console.log($("#isFileChanged").val());
	$("#isFileChanged").val('Y');
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

removeFile=function(elem) {
	
	$("#isFileChanged").val('Y');
	
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

changeFilename=function(file) {
	
	$("#isFileChanged").val('Y');

	const filename = $(file)[0].files[0].name;
	const target = $(file).prevAll('input.upload-name');
	target.val(filename);
	$(file).prevAll('input[name="fileIdxs"]').remove();
}


let openModal=function(bc_no, cu_id, bc_content){
	$("#commentModal").modal("toggle");

		document.getElementById("modalWriter").value = cu_id;
		document.getElementById("modalContent").value = bc_content;

		document.getElementById("btnCommentUpdate").setAttribute("onclick", "updateComment("+ bc_no +")");
}


let updateComment=function(bc_no) {
	let page = $("#page").val();
	let b_no = $("#no").val();
	let data = {
		bc_no:bc_no,
		bc_content:$("#modalContent").val()
	}
		$.ajax({ //비동기호출
			url:`/api/board/${b_no}/comment`,
			type:"PUT",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
			}).done(function(resp){
				$("#commentModal").modal("hide");
				alert("댓글수정을 성공하였습니다!");
				location.href=`/board/${page}/${b_no}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});

}

let check=function(){
    	var reg = $(".chk");
    	$.each(reg, function(){
			if($(this).val() == "" || $(this).val() == null){
				return true;
			}
		})
   	}
 
let deleteBoard=function(b_no,page){
	
	if (confirm(b_no + "번 게시글을 삭제할까요?")) {
		$.ajax({
			url:`/api/board/${b_no}`,
			type:"DELETE",
			dataType:"json"
			}).done(function(resp){
				alert("글삭제를 성공하였습니다!");
				location.href="/board/list?page="+page;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
	}
	
}

let searchBoard=function(form) {
	alert("함수작동")
				/*[- 드롭다운이 아닌 메인 검색 키워드로 검색했을 때 -]*/
				if (isEmpty(form) == true) {
					var searchKeyword = document.getElementById("mainSearchKeyword");
					if (isEmpty(searchKeyword.value) == true) {
						alert("키워드를 입력해 주세요.");
						searchKeyword.focus();
						return false;
					}

					form = document.getElementById("searchForm");
					form.searchKeyword.value = searchKeyword.value;
					form.submit();
				}

				if (isEmpty(form.searchKeyword.value) == true) {
					alert("키워드를 입력해 주세요.");
					form.searchKeyword.focus();
					return false;
				}
			}
			/*[- end of function -]*/

let index={
   	
	init:function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
		$("#btn-delete").on("click",()=>{
			this.deleteById();
		});
		$("#btn-comment-save").on("click",()=>{
			this.commentSave();
		});
		$("#btn-search").on("click",()=>{
			this.search();
		});
		
	},
	
	save:function(){
		var formData = new FormData();
		// 넘길 데이터를 담아준다. 
		let data = {
				b_title:$("#title").val(),
				b_content:$("#content").val()
			}
		
		// input class 값 
		var fileInput = $('.files');
		// fileInput 개수를 구한다.
		for (var i = 0; i < fileInput.length; i++) {
			if (fileInput[i].files.length > 0) {
				for (var j = 0; j < fileInput[i].files.length; j++) {
					console.log(" fileInput[i].files[j] :::"+ fileInput[i].files[j]);
					
					// formData에 'file'이라는 키값으로 fileInput 값을 append 시킨다.  
					formData.append('files', $('.files')[i].files[j]);
				}
			}
		}

		// 'key'라는 이름으로 위에서 담은 data를 formData에 append한다. type은 json  
		formData.append('key', new Blob([ JSON.stringify(data) ], {type : "application/json"}));
		
		$.ajax({ //비동기호출
			url:"/api/board",
			type:"POST",
			enctype: 'multipart/form-data',        
			data: formData,        
			processData: false,        
			contentType: false
			}).done(function(resp){
				alert("글쓰기를 성공하였습니다!");
				console.log(resp)
				location.href="/board/list";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	},
	
	update:function(){
		let page = $("#page").val();
		let b_no = $("#no").val();
		
		var formData = new FormData();
		// 넘길 데이터를 담아준다. 
		let data = {
			b_title:$("#title").val(),
			b_content:$("#content").val(),
			isFileChanged:$("#isFileChanged").val()
		}
		
		// input class 값 
		var fileInput = $('.files');
		// fileInput 개수를 구한다.
		for (var i = 0; i < fileInput.length; i++) {
			if (fileInput[i].files.length > 0) {
				for (var j = 0; j < fileInput[i].files.length; j++) {
					console.log(" fileInput[i].files[j] :::"+ fileInput[i].files[j]);
					
					// formData에 'file'이라는 키값으로 fileInput 값을 append 시킨다.  
					formData.append('files', $('.files')[i].files[j]);
				}
			}
		}
		
		// 'key'라는 이름으로 위에서 담은 data를 formData에 append한다. type은 json  
		formData.append('key', new Blob([ JSON.stringify(data) ], {type : "application/json"}));
		
		
		
		
		
		$.ajax({ //비동기호출
			url:"/api/board/"+b_no,
			type:"POST",
			enctype: 'multipart/form-data',        
			data: formData,  
			processData: false,        
			contentType: false      
			}).done(function(resp){
				alert("글수정을 성공하였습니다!");
				console.log(resp)
				location.href=`/board/${page}/${b_no}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	},
	
	deleteById:function(){
		if (!confirm('글을 삭제하시겠어요?')) {
			return false;
		}
		let page = $("#page").val();
		let b_no = $("#no").val();
		$.ajax({ //비동기호출
			url:"/api/board/"+b_no,
			type:"DELETE",
			}).done(function(resp){
				alert("글삭제를 성공하였습니다!");
				console.log(resp)
				location.href="/board/list?page="+page;
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	},
	
	commentSave:function(){
		let page = $("#page").val();
		let b_no = $("#no").val();
		let data = {
			bc_content:$("#comment-content").val(),
		}
		$.ajax({ //비동기호출
			url:`/api/board/${b_no}/comment`,
			type:"POST",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
			}).done(function(resp){
				alert("댓글작성을 성공하였습니다!");
				location.href=`/board/${page}/${b_no}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
	},
	
	deleteComment:function(b_no, bc_no){
		if (!confirm('댓글을 삭제하시겠어요?')) {
			return false;
		}
		let page = $("#page").val();
		$.ajax({ //비동기호출
			url:`/api/board/${b_no}/comment/${bc_no}`,
			type:"DELETE",
			dataType:"json"
			}).done(function(resp){
				alert("댓글삭제를 성공하였습니다!");
				location.href=`/board/${page}/${b_no}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
	}
	
}

index.init();