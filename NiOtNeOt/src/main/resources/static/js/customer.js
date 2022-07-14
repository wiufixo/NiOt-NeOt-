//무한 스크롤 설정
var loading = false;
var scrollPage = 1;

//카카오 주소
var execDaumPostcode= function(){
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address_detail").focus();
            }
        }).open();
    }

//transExpantion paging 처리 함수
var transExpantionPaging = function(page){
	$.ajax({
		url:"/customer/transPaging",
		type:"get",
		data:{
			"page":page,
			"cu_no":$("#trans_cu_no").val()
			},
		success:function(data){
			$("#trans_product").empty();
			//반복문
			for(var i=0;i<data.length;i++){
				var product=data[i].product;
				var div=$("<div></div>");
				$(div).attr("id","product_"+i);
				$(div).attr("class","product_status");
				$(div).attr("value",data[i].p_no)
				div.append($("<span></span>",{text:product.pr_no}));
				div.append($("<span></span>",{text:product.pr_title}));
				div.append($("<span></span>",{text:product.pr_content}));
				div.append($("<span></span>",{text:product.pr_created}));
				div.appendTo("#trans_product");
			}
		}
	})
}

//wishExpantion paging 처리 함수
var wishExpantionPaging = function(page){
	$.ajax({
		url:"/customer/wishPaging",
		type:"get",
		data:{
			"page":page,
			"cu_no":$("#wish_cu_no").val()
			},
		success:function(data){
			$("#wish_product").empty();
			//반복문
			for(var i=0;i<data.length;i++){
				var product=data[i].product;
				var div=$("<div></div>");
				$(div).attr("id","product_"+i);
				$(div).attr("class","product_status");
				$(div).attr("value",data[i].p_no)
				div.append($("<span></span>",{text:product.pr_no}));
				div.append($("<span></span>",{text:product.pr_title}));
				div.append($("<span></span>",{text:product.pr_content}));
				div.append($("<span></span>",{text:product.pr_created}));
				div.appendTo("#wish_product");
			}
		}
	})
}

//productExpantion paging 처리 함수
var productExpantionPaging = function(page){
	$.ajax({
		url:"/customer/productPaging",
		type:"get",
		data:{
			"page":page,
			"cu_no":$("#product_cu_no").val()
			},
		success:function(data){
			console.log(data);
			$("#product_product").empty();
			//반복문
			for(var i=0;i<data.length;i++){
				var div=$("<div></div>");
				$(div).attr("id","product_"+i);
				$(div).attr("class","product_status");
				$(div).attr("value",data[i].p_no)
				div.append($("<span></span>",{text:data[i].pr_no}));
				div.append($("<span></span>",{text:data[i].pr_title}));
				div.append($("<span></span>",{text:data[i].pr_content}));
				div.append($("<span></span>",{text:data[i].pr_created}));
				div.appendTo("#product_product");
			}
		}
	})
}

//feedExpantion 무한 스크롤 함수
var feedExpantionScroll = function(page){
	if(!loading){
		loading=true;
		
		$.ajax({
		url:"/customer/feedLoad",
		type:"get",
		data:{
			"page":page,
			"customer":$("#feed_cu_no").val()
			},
		dataType:"json",
		success:function(data){
			for(var i=0;i<data.length;i++){
				var div=$("<div></div>");
				$(div).attr("id","feed_"+i);
				$(div).attr("class","feed_status");
				$(div).attr("value",data[i].f_no)
				div.append($("<span></span>",{text:data[i].f_no}));
				div.append($("<span></span>",{text:data[i].f_title}));
				div.append($("<span></span>",{text:data[i].f_content}));
				div.append($("<span></span>",{text:data[i].f_created}));
				div.appendTo("body");
			}
			loading=false;
			scrollPage+=1;
		}
	});
	}
}

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
			for(var i=0;i<data.length;i++){
				var div=$("<div></div>");
				$(div).attr("id","board_"+i);
				$(div).attr("class","board_status");
				$(div).attr("value",data[i].b_no)
				div.append($("<span></span>",{text:data[i].b_no}));
				div.append($("<span></span>",{text:data[i].b_title}));
				div.append($("<span></span>",{text:data[i].b_content}));
				div.append($("<span></span>",{text:data[i].b_created}));
				div.appendTo("body");
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
				if(data[i].cu_img=="defaultUserImg.jpg"){
					div.append($('<img/>',{src:"/image/customer/defaultUserImg", class:"profile_img_small"}))
				}else{
					div.append($('<img/>',{src:"/image/customer/"+data[i].cu_no+"/"+data[i].cu_img, class:"profile_img_small"}));
				}
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
				if(data[i].cu_img=="defaultUserImg.jpg"){
					div.append($('<img/>',{src:"/image/customer/defaultUserImg", class:"profile_img_small"}))
				}else{
					div.append($('<img/>',{src:"/image/customer/"+data[i].cu_no+"/"+data[i].cu_img, class:"profile_img_small"}));
				}
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

//개인정보 페이지로 갈 때 userpage나 mypage를 구분하여 보내준다
var page_choice=function(no){
	var form=$("<form></form>");
	form.attr("method","get");
	form.attr("action","/customer/pageChoice");
	form.append($('<input/>',{type:"hidden",name:"user_no",value:no}));
	form.appendTo("body");
	form.submit();
}

	//확장페이지로 갈 때 user인지 mypage인지 구분하고 보내준다
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
		//아직 팔로우하지 않았을 경우
	}else if($("#userpage_follow_check").val()==0){
		$.ajax({
			url:"/customer/insertFollow",
			method:"post",
			data:{
				cu_no:$("#userpage_cu_no").val(),
				user_no:$("#userpage_user_no").val()
			},
			success:function(){window.location.reload();}
	})		
		//이미 팔로우 되어있었을 경우
	}else if($("#userpage_follow_check").val()==1){
		$.ajax({
			url:"/customer/deleteFollow",
			method:"post",
			data:{
				cu_no:$("#userpage_cu_no").val(),
				user_no:$("#userpage_user_no").val(),
			},
			success:function(){window.location.reload();}
		})
	}
})

	//productExpantion에서 product 클릭
$(document).on("click",".product_status",function(e){
	e.stopImmediatePropagation();
	var product_id=$(this).attr("id");
	var product_no=$("."+product_id).text();
	window.location.href="/product/"+product_no;
})

	//feedExpantion에서 feed 클릭
$(document).on("click",".board_status",function(e){
	e.stopImmediatePropagation();
	var feed_id=$(this).attr("id");
	var feed_no=$("."+feed_id).text();
	window.location.href="/feed/"+feed_no;
})

	//boardExpantion에서 board 클릭
$(document).on("click",".board_status",function(e){
	e.stopImmediatePropagation();
	var board_id=$(this).attr("id");
	var board_no=$("."+board_id).text();
	window.location.href="/board/1/"+board_no;
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
			success:function(data){
				$(opener.document).find("#mypage_my_img").attr("src","/image/customer/"+$(opener.document).find("#mypage_no").val()+"/"+data);
				alert("uploaded");
			},
			async: false,
		})
		window.close();
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
			data:{cu_no:$("#delete_no").val()},
			success:function(){
				alert("customer deleted");
				window.location.href="/index";
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

//join 이메일 인증 클릭
$(document).on("click","#join_email_check_start",function(){
	alert("인증번호가 발송되었습니다");
	//이메일로 인증번호 발송
	$.ajax({
		url:"/customer/checkSend",
		method:"post",
		data:{cu_email:$("#cu_email").val()},
		success:function(data){
			$("#join_email_check_true").val(data);
		}
	})
})
//join 회원 가입 버튼 클릭
$(document).on("click","#join_button",function(){
	var check=0;
	
	//비밀번호 확인 체크
	if($("#cu_pwd").val()!=$("#cu_pwd_check").val() || $("#cu_pwd").val()==""){
		check++;
		$("#join_pwd_warning").css({
			"display":"inline"
		});
	}else{
		$("#join_pwd_warning").css({
			"display":"none"
		});
	}
	//이메일 인증했는지 확인
	if($("#join_email_check").val()!=$("#join_email_check_true").val()){
		check++;
		$("#join_email_check_warning").css({"display":"inline"});
	}else{
		$("#join_email_check_warning").css({"display":"none"});
	}
	//join 실행
	if(check==0){$("#joinForm").submit();}
})

//update 회원 정보 수정 버튼 클릭
$(document).on("click","#update_button",function(){
	var check=0;	
	//비밀번호 확인 체크
	if($("#cu_pwd").val()!=$("#cu_pwd_check").val()){
		check++;
		$("#update_pwd_warning").css({"display":"inline"});
	}else{
		$("#update_pwd_warning").css({"display":"none"});
	}
	//확인 후 updateForm 보내기
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
		data: {cu_id: cu_id},
		success: function(data) {
			if (data.cu_no != 0) {
				//email로 새로운 비밀번호 전송
				$.ajax({
					url:"/customer/pwdSend",
					method:"post",
					data:{cu_id:cu_id},
					success:function(){
						alert("이메일로 비밀번호가 발송되었습니다");
						window.location.href="/customer/login";
					}
				})
			} else if (data.cu_no == 0) {
				$("#find_warning").css({"display": "inline"})
			}
		}
	});
})

$(document).on("click",".product_paging_number",function(){
	productExpantionPaging($(this).text());
})

$(document).on("click",".wish_paging_number",function(){
	wishExpantionPaging($(this).text());
})

$(document).on("click",".trans_paging_number",function(){
	transExpantionPaging($(this).text());
})

$(document).ready(function() {
	//무한 스크롤 페이지 불러오기
	$(window).scroll(function(){
		var scrollNow=$(window).scrollTop();
		if(scrollNow+$(window).height()+100>=$("body").height()){
			//page 이름으로 불러올 무한스크롤 정보 탐색
			if(location.pathname=="/customer/followerExpantion"){
				followerScroll(scrollPage);
			}else if(location.pathname=="/customer/followingExpantion"){
				followingScroll(scrollPage);
			}else if(location.pathname=="/customer/boardExpantion"){
				boardExpantionScroll(scrollPage);
			}else if(location.pathname=="/customer/feedExpantion"){
				feedExpantionScroll(scrollPage);
			}
		}
	})
})

window.onload = function(){
	if(location.pathname=="/customer/productExpantion"){
		productExpantionPaging(1);
	}
	if(location.pathname=="/customer/wishExpantion"){
		wishExpantionPaging(1);
	}
	if(location.pathname=="/customer/transExpantion"){
		transExpantionPaging(1);
	}
}