$(document).ready(function() {
	//mypage 회원 정보 수정 클릭
	$("#mypage_update_button").on("click",function(){
		window.location.href="/customer/update";
	})
	
	//mypage 회원 탈퇴 버튼 클릭
	$("#mypage_delete_customer_button").on("click",function(){
		$.ajax({
			url:"/customer/deleteCustomer",
			method:"post",
			data:{
				cu_no:$("mypage_no").val()
			},
			success:function(data){
				window.location.href="/customer/join";
			}
		})
	})
	
	//join 회원 가입 버튼 클릭
	$("#join_button").on("click",function(){
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
		console.log(check);
		if(check==0){
			$("#joinForm").submit();
		}
	})
	
	//update 회원 정보 수정 버튼 클릭
	$("#update_button").on("click",function(){
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
		
		if(check==0){
			$("#updateForm").submit();
		}
	})
	
	//findpwd 비밀번호 찾기 버튼 클릭
	$("#find_pwd_button").on("click", function() {
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
						success:function(data){
							window.location.href="/customer/findPwd";
							alert("비밀번호가 발송되었습니다");
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
	})

window.onload = function(){
	//update에서 체크박스 표시
	if ($("#update_cu_gender").val() == 0) {
		$("#update_woman").prop("checked", true);
	} else if ($("#update_cu_gender").val() == 1) {
		$("#update_man").prop("checked", true);
	}

	if ($("#update_privacy_agree").val() == 0) {
		$("#update_deny").prop("checked", true);
	} else if ($("#update_privacy_agree").val() == 1) {
		$("#update_agree").prop("checked", true);
	}
	
	if($("#mypage_gender").val()==0){
		$("#mypage_gender").text("여");
	}else if($("#mypage_gender").val()==1){
		$("#mypage_gender").text("남");
	}
}