$(document).ready(function() {
	
	//deleteCheck에서 인증 후 회원 탈퇴 클릭
	$("#delete_delete_button").on("click",function(){
		console.log($("#delete_check_num_true").val());
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
				success:function(data){
					alert("customer deleted");
					window.location.href="/customer/join";
				}
			})
		}
	})
	
	//deleteCheck에서 이메일 인증 클릭
	$("#delete_check_button").on("click",function(){
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
					console.log(data);
				}
				})
			}
		})
		
	//mypage my_img 클릭
	$("#mypage_my_image").on("click"),function(){
		
	}
		
	//mypage_feed_more 클릭
	$("#mypage_feed_more").on("click",function(){
		window.location.href="/customer/feedExpantion";
	})
	
	//mypage_product_more 클릭
	$("#mypage_product_more").on("click",function(){
		window.location.href="/customer/productExpantion";
	})

	//mypage_board_more 클릭
	$("#mypage_board_more").on("click",function(){
		window.location.href="/customer/boardExpantion";
	})

	
	//mypage_trans 클릭
	$("#mypage_trans").on("click",function(){
		window.location.href="/customer/transExpantion";
	})
	
	//mypage_wish 클릭
	$("#mypage_wish").on("click",function(){
		window.location.href="/customer/wishExpantion";
	})
	
	//mypage follwer 클릭
	$("#mypage_follower").on("click",function(){
		window.location.href="/customer/followerExpantion";
	})
	
	//mypage following 클릭
	$("#mypage_following").on("click",function(){
		window.location.href="/customer/followingExpantion";
	})
	
	//mypage 회원 정보 수정 클릭
	$("#mypage_update_button").on("click",function(){
		window.location.href="/customer/update";
	})
	
	//mypage 회원 탈퇴 버튼 클릭
	$("#mypage_delete_button").on("click",function(){
		window.location.href="/customer/deleteCheck"
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
		
		//updateForm 보내기
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
							alert("이메일로 비밀번호가 발송되었습니다");
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
	
	//mypage에서 성별 표시
	if($("#mypage_gender").val()==0){
		$("#mypage_gender").text("여");
	}else if($("#mypage_gender").val()==1){
		$("#mypage_gender").text("남");
	}
}