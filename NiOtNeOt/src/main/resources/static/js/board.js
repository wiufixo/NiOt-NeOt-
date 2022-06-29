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
	},
	
	save:function(){
		let data = {
			b_title:$("#title").val(),
			b_content:$("#content").val(),
		}
		
		console.log(data);
		
		$.ajax({ //비동기호출
			url:"/api/board",
			type:"POST",
			data:JSON.stringify(data), // http body 데이터
			contentType:"application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType:"json" // 요청에 의한 응답이 왔을때의 데이터 ==> 기본적으로 받는건 모두 문자열인데 생긴게 json이라고 알려주니 js오브젝트로 변환하여 응답해줌
							// ===> 생략해도 json으로 변환해준다 기능의 발전!
			}).done(function(resp){
				alert("글쓰기를 성공하였습니다!");
				console.log(resp)
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	},
	
	update:function(){
		let b_no = $("#no").val();
		let data = {
			b_title:$("#title").val(),
			b_content:$("#content").val(),
		}
		
		$.ajax({ //비동기호출
			url:"/api/board/"+b_no,
			type:"POST",
			data:JSON.stringify(data), // http body 데이터
			contentType:"application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType:"json" // 요청에 의한 응답이 왔을때의 데이터 ==> 기본적으로 받는건 모두 문자열인데 생긴게 json이라고 알려주니 js오브젝트로 변환하여 응답해줌
							// ===> 생략해도 json으로 변환해준다 기능의 발전!
			}).done(function(resp){
				alert("글수정을 성공하였습니다!");
				console.log(resp)
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	},
	
	deleteById:function(){
		let b_no = $("#b_no").text();
		$.ajax({ //비동기호출
			url:"/api/board/"+b_no,
			type:"DELETE",
			}).done(function(resp){
				alert("삭제를 성공하였습니다!");
				console.log(resp)
				location.href="/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); //ajax통신을 이용하여 3개의 데이터를 json의 형식으로 회원가입정보 insert요청
	}
}

index.init();