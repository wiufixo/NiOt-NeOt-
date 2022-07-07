//무한 스크롤 설정
var loading = false;
var scrollPage = 1;

//boardExpantion 무한 스크롤 함수
var boardExpantionScroll = function(page){
	if(!loading){
		loading=true;
		
		$.ajax({
		url:"/customer/boardLoad",
		type:"get",
		data:{
			"page":page,
			"customer":$("#board_cu_no").val()
			},
		dataType:"json",
		success:function(data){
			console.log(data);
			for(var i=0;i<data.length;i++){
			}
			loading=false;
			scrollPage+=1;
		}
		});
	}
}

//following 무한 스크롤 함수
var followingScroll = function(page){
	if(!loading){
		loading=true;
		
		$.ajax({
		url:"/customer/followingLoad",
		type:"get",
		data:{
			"page":page,
			"customer":$("#following_cu_no").val()
			},
		dataType:"json",
		success:function(data){
			for(var i=0;i<data.length;i++){
				var div=$("<div></div>");
				$(div).attr("class","following_status");
				$(div).attr("id","following_"+i);
				$(div).attr("value",data[i].cu_no);
				div.append($('<img/>',{src:"/image/"+data[i].cu_img, class:"profile_img_small"}));
				div.append($("<span></span>",{text:data[i].cu_name}));
				div.append($("<span></span>",{text:data[i].cu_nickname}));
				div.append($("<span></span>",{text:data[i].cu_email}));
				div.appendTo("body");
			}
			loading=false;
			scrollPage+=1;
		}
		});
	}
}

//follower 무한 스크롤 함수
var followerScroll = function(page){
	if(!loading){
		loading=true;
		
		$.ajax({
		url:"/customer/followerLoad",
		type:"get",
		data:{
			"page":page,
			"customer":$("#follower_cu_no").val()
			},
		dataType:"json",
		success:function(data){
			for(var i=0;i<data.length;i++){
				var div=$("<div></div>");
				$(div).attr("class","follower_status");
				$(div).attr("id","follower_"+i);
				$(div).attr("value",data[i].cu_no);
				div.append($('<img/>',{src:"/image/"+data[i].cu_img, class:"profile_img_small"}));
				div.append($("<span></span>",{text:data[i].cu_name}));
				div.append($("<span></span>",{text:data[i].cu_nickname}));
				div.append($("<span></span>",{text:data[i].cu_email}));
				div.appendTo("body");
			}
			loading=false;
			scrollPage+=1;
		}
		});
	}
}

//userpage나 mypage로 보내주는 함수
var page_choice=function(no){
	var form=$("<form></form>");
	form.attr("method","get");
	form.attr("action","/customer/pageChoice");
	form.append($('<input/>',{type:"hidden",name:"user_no",value:no}));
	form.appendTo("body");
	
	form.submit();
}

	//user인지 mypage인지 구분하고 확장페이지로 보내줌
var page_check_helper=function(check_no,action){
	//check_no:0이면 mypage, 0보다 크면 해당 유저 no
	var form=$("<form></form>");
	form.attr("method","get");
	form.attr("action",action);
	form.append($('<input/>',{type:"hidden",name:"user_no",value:check_no}));
	form.appendTo("body");
	
	form.submit();
}

	//userpage follow 클릭
$(document).on("click","#userpage_follow_button",function(){
	//로그인하지 않았을 경우
	if($("#userpage_cu_no").val()==0){
		window.location.href="/customer/login";
	}else if($("#userpage_follow_check").val()==0){
		$.ajax({
			url:"/customer/insertFollow",
			method:"post",
			data:{
				cu_no:$("#userpage_cu_no").val(),
				user_no:$("#userpage_user_no").val()
			},
			success:function(){
				window.location.reload();
			}
	})		
	}else if($("#userpage_follow_check").val()>=1){
		$.ajax({
			url:"/customer/deleteFollow",
			method:"post",
			data:{
				cu_no:$("#userpage_cu_no").val(),
				user_no:$("#userpage_user_no").val(),
			},
			success:function(){
				window.location.reload();
			}
		})
	}
})

	//followerExpantion에서 user 클릭
$(document).on("click",".follower_status",function(e){
	e.stopImmediatePropagation();
	var follower_id=$(this).attr('id');
	var follower_no=$("."+follower_id).val();
	page_choice(follower_no);
})

	//followingExpantion에서 user 클릭
$(document).on("click",".following_status",function(e){
	e.stopImmediatePropagation();
	var following_id=$(this).attr('id');
	var following_no=$("."+following_id).val();
	page_choice(following_no);
})

	//mypage changeImg에서 confirm 클릭
$(document).on("click","#change_img_confirm",function(){
	var img=$("#change_img_file_upload")[0].files[0];
	var formData=new FormData();
	var inputFile=$("input[name='change_img_file_upload']");
	var files=inputFile[0].files;
	if(img==null){
		alert("이미지를 넣어주세요");
	}else if(img!=null){
			formData.append("uploadFile",files[0]);
		$.ajax({
			url:"/customer/changeImgProcess",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			success:function(){
				alert("uploaded");
				window.close();
				opener.location.reload();
			},
			async: false,
		})
	}
})

//deleteCheck에서 인증 후 회원 탈퇴 클릭
$(document).on("click","#delete_delete_button",function(){
	if($("#delete_check_num_true").val()!=$("#delete_check_num").val()){
		$("#delete_delete_warning").css({
			"display":"inline"
		});
	}else{
		$("delete_delete_warning").css({
			"display":"none"
		});
		$.ajax({
			url:"/customer/deleteCustomer",
			method:"post",
			data:{
				cu_no:$("#delete_no").val()
			},
			success:function(){
				alert("customer deleted");
				window.location.href="/";
			}
		})
	}
})

//deleteCheck에서 이메일 인증 클릭
$(document).on("click","#delete_check_button",function(){
	if($("#delete_email").val()!=$("#delete_email_origin").val()){
		$("#delete_email_warning").css({
			"display":"inline"
		});
	}else{
		$("#delete_email_warning").css({
			"display":"none"
		});
		
		$("#delete_check_num_span").css({
			"visibility":"visible"
		});
		$("#delete_delete_button").css({
			"visibility":"visible"
		});
		$.ajax({
			url:"/customer/checkSend",
			method:"post",
			data:{
				cu_email:$("#delete_email_origin").val()
			},
			success:function(data){
				$("#delete_check_num_true").val(data);
			}
			})
		}
})
		
		
//userpage_feed_more 클릭
$(document).on("click","#userpage_feed_more",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/feedExpantion")
})

//userpage_product_more 클릭
$(document).on("click","#userpage_product_more",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/productExpantion")
})

//userpage_board_more 클릭
$(document).on("click","#userpage_board_more",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/boardExpantion")
})

//userpage_trans 클릭
$(document).on("click","#userpage_trans",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/transExpantion")
})

//userpage_wish 클릭
$(document).on("click","#userpage_wish",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/wishExpantion")
})

//userpage_follower 클릭
$(document).on("click","#userpage_follower",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/followerExpantion")
})

//userpage_following 클릭
$(document).on("click","#userpage_following",function(){
	page_check_helper($("#userpage_user_no").val(),"/customer/followingExpantion")
})

//mypage my_img 클릭
$(document).on("click","#mypage_my_img",function(){
	console.log("good");
	window.open("/customer/changeImg","_blank","height:300,width:300");
})
	
//mypage_feed_more 클릭
$(document).on("click","#mypage_feed_more",function(){
	page_check_helper(0,"/customer/feedExpantion");
})

//mypage_product_more 클릭
$(document).on("click","#mypage_product_more",function(){
	page_check_helper(0,"/customer/productExpantion");
})

//mypage_board_more 클릭
$(document).on("click","#mypage_board_more",function(){
	page_check_helper(0,"/customer/boardExpantion");
})


//mypage_trans 클릭
$(document).on("click","#mypage_trans",function(){
	page_check_helper(0,"/customer/transExpantion");
})

//mypage_wish 클릭
$(document).on("click","#mypage_wish",function(){
	page_check_helper(0,"/customer/wishExpantion");
})

//mypage follwer 클릭
$(document).on("click","#mypage_follower",function(){
	page_check_helper(0,"/customer/followerExpantion");
})

//mypage following 클릭
$(document).on("click","#mypage_following",function(){
	page_check_helper(0,"/customer/followingExpantion");
})

//mypage 회원 정보 수정 클릭
$(document).on("click","#mypage_update_button",function(){
	window.location.href="/customer/update";
})

//mypage 회원 탈퇴 버튼 클릭
$(document).on("click","#mypage_delete_button",function(){
	window.location.href="/customer/deleteCheck"
})

//join 회원 가입 버튼 클릭
$(document).on("click","#join_button",function(){
	var check=0;
	
	//id 중복 체크
	$.ajax({
		url:"/customer/idCheck",
		method:"post",
		data:{
			cu_id:$("#cu_id").val()
		},
		success: function(data){
			if(data==0){
				$("#join_id_warning").css({
					"display":"none"
				})
			}else if(data!=0){
				check++;
				$("#join_id_warning").css({
					"display":"inline"
				});
			}
		},
		 async: false
	})
	
	//email 중복 체크
	$.ajax({
		url:"/customer/emailCheck",
		method:"post",
		data:{
			cu_email:$("#cu_email").val()
		},
		success: function(data){
			if(data==0){
				$("#join_email_warning").css({
					"display":"none"
				})
			}else if(data!=0){
				check++;
				$("#join_email_warning").css({
					"display":"inline"
				});
		}
		},
		async: false
	})
	
	//nickname 중복 체크
	$.ajax({
		url:"/customer/nicknameCheck",
		method:"post",
		data:{
			cu_nickname:$("#cu_nickname").val()
		},
		success:function(data){
			if(data==0){
				$("#join_nickname_warning").css({
					"display":"none"
				})
			}else if(data!=0){
				check++;
				$("#join_nickname_warning").css({
					"display":"inline"
				});
		}
		},
		async: false
	})
	
	//비밀번호 확인 체크
	if($("#cu_pwd").val()!=$("#cu_pwd_check").val()){
		check++;
		$("#join_pwd_warning").css({
			"display":"inline"
		});
	}else{
		$("#join_pwd_warning").css({
			"display":"none"
		});
	}
	//join 실행
	if(check==0){
		$("#joinForm").submit();
	}
})

//update 회원 정보 수정 버튼 클릭
$(document).on("click","#update_button",function(){
	var check=0;
	
	//email 중복 체크
	$.ajax({
		url:"/customer/emailCheck",
		method:"post",
		data:{
			cu_email:$("#cu_email").val()
		},
		success: function(data){
			if(data==0||$("#cu_email").val()==$("#cu_email_origin").val()){
				$("#update_email_warning").css({
					"display":"none"
				})
			}else if(data!=0&&$("#cu_email").val()==$("#cu_email_origin").val()){
				check++;
				$("#update_email_warning").css({
					"display":"inline"
				});
		}
		},
		async: false
	})
	
	//nickname 중복 체크
	$.ajax({
		url:"/customer/nicknameCheck",
		method:"post",
		data:{
			cu_nickname:$("#cu_nickname").val()
		},
		success:function(data){
			if(data==0||$("#cu_nickname").val()==$("#cu_nickname_origin").val()){
				$("#update_nickname_warning").css({
					"display":"none"
				})
			}else if(data!=0&&$("#cu_nickname").val()==$("#cu_nickname_origin").val()){
				check++;
				$("#update_nickname_warning").css({
					"display":"inline"
				});
		}
		},
		async: false
		})
		
	//비밀번호 확인 체크
	if($("#cu_pwd").val()!=$("#cu_pwd_check").val()){
		check++;
		$("#update_pwd_warning").css({
			"display":"inline"
		});
	}else{
		$("#update_pwd_warning").css({
			"display":"none"
		});
	}
	
	//updateForm 보내기
	if(check==0){
		$("#updateForm").submit();
	}
})

//findpwd 비밀번호 찾기 버튼 클릭
$(document).on("click","#find_pwd_button", function() {
	var cu_id = $("#cu_id").val();
	$.ajax({
		url: "/customer/findByCu_id",
		method: "post",
		data: {
			cu_id: cu_id
		},
		success: function(data) {
			if (data.cu_no != 0) {
				//email로 비밀번호 전송
				$.ajax({
					url:"/customer/pwdSend",
					method:"post",
					data:{
						cu_id:cu_id
					},
					success:function(){
						alert("이메일로 비밀번호가 발송되었습니다");
						window.location.href="/customer/login";
					}
				})
			} else if (data.cu_no == 0) {
				$("#find_warning").css({
					"display": "inline"
				})
			}
		}
	});
})

$(document).ready(function() {
	//무한 스크롤 페이지 불러오기
	$(window).scroll(function(){
		var scrollNow=$(window).scrollTop();
		console.log(scrollNow);
		console.log($(window).height());
		if(scrollNow+$(window).height()+100>=$("body").height()){
			//page 이름으로 불러올 무한스크롤 정보 탐색
			if(location.pathname=="/customer/followerExpantion"){
				followerScroll(scrollPage);
			}else if(location.pathname=="/customer/followingExpantion"){
				followingScroll(scrollPage);
			}else if(location.pathname=="/customer/boardExpation"){
				boardExpantionScroll(scrollPage);
			}
		}
	})
})

window.onload = function(){

}