$(document).ready(function() {
	//회원 가입 버튼 클릭
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
	
	//회원 정보 수정 버튼 클릭
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
					window.location.href = "";
					//email test 후 비밀번호 전송
				} else if (data.cu_no == 0) {
					//hidden된 메시지 출력
					$("#find_warning").css({
						"display": "inline"
					})
				}
			}
		});
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
}