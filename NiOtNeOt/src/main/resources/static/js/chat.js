
function getId(id) {
	return document.getElementById(id);
}

var data = {};//전송 데이터(JSON)

var ws;
var cu_id = getId('cu_id');
var btnLogin = getId('btnLogin');
var btnSend = getId('btnSend');
var talk = getId('talk');
var msg = getId('msg');
var imgSrc = "../image/chat/";

ws = new WebSocket("ws://" + location.host + "/chatt"+"/"+$("#cr_no").val());	// 양방향 통신용 웹소켓 생성

$(function() {
	var crn = $("#cr_no").val();
	
	var cr_no_json = {
		"cr_no": crn
	};
	
	$.ajax({
		url: "/refreshChat",
		data: cr_no_json,
		success: function(data) {
			$.each(data, function(index, item) {
				var css;
				if (data[index].customer.cu_id == cu_id.value) {
					css = 'class=me';
				} else {
					css = 'class=other';
				}
				if(data[index].ch_img == null) {
					
					var item = `<div ${css} >
			                [ ${data[index].ch_created} ]<br/>
			                <span><b>${data[index].customer.cu_id}</b></span><br/>
	                     	<span>${data[index].ch_content}</span>
							</div>`;
				} else {
					var item = `<div ${css} >
			                 [ ${data[index].ch_created} ]<br/>
			                 <span><b>${data[index].customer.cu_id}</b></span><br/>
	                     	<img id="chatImg" src=${imgSrc}${data[index].ch_img}><br/>
	                     	<span>${data[index].ch_content}</span>
							</div>`;
				}
				
				talk.innerHTML += item;
			});
		}, async: false
	});
	setTimeout(function() {
		scroll();
	}, 50);
})

function scroll() {
	console.log(talk.scrollTop);
	console.log(talk.scrollHeight);
	talk.scrollTop = talk.scrollHeight;//스크롤바 하단으로 이동
}

ws.onmessage = function(msg) {
	var data = JSON.parse(msg.data);
	var css;
	var data2;
	console.log(data)
	
	if (data.cu_id == cu_id.value) { // 자신 아이디인지 상대 아이디인지에 따라 적용되는 CSS를 달리 함
		css = 'class=me';
	} else {
		css = 'class=other';
	}
	
	if (data.type == 0) {
		var item = `<div ${css} >
						[ ${data.date} ]<br/>
				    	<span><b>${data.cu_id}</b></span> <br/>
		                <span>${data.msg}</span>
					</div>`;
	} else {
		var crn = $("#cr_no").val();
		var item ="";
		
		var data = {
			"cr_no": crn
		};
		
		$.ajax({            
			url: "/findRecentChat",
			data: data,
			dataType: 'json',
			success: function(message) {
				data2 = message;
				console.log("data2 : ");
				console.log(data2);
			}, async: false
		}) 
		setTimeout(function() {
		}, 100);
		item = `<div ${css} >
			[ ${data2[0].ch_created} ]<br/>
			<span><b>${data2[0].customer.cu_id}</b></span><br/>
			<img id="chatImg" src=${imgSrc}${data2[0].ch_img}><br/>
			<span>${data2[0].ch_content}</span>
		</div>`;
	}
	
	talk.innerHTML += item;
	
	setTimeout(function() {
		scroll();
	}, 300);
}

msg.onkeyup = function(ev) {	// 엔터 키로 전송 가능
	if (ev.keyCode == 13) {
		send();
	}
}

btnSend.onclick = function() {	// 전송 버튼으로 전송 가능
	send();
}

$("#createroom").onclick = function() {	// 전송 버튼으로 전송 가능
	
}

function insert() {
	var cuno = $("#cu_no").val();
	var crn = $("#cr_no").val();
	var ms = $("#msg").val();
	
	var data = {
		"cu_no": cuno
		, "cr_no": crn
		, "ch_content": ms
	};
	
	$.ajax({            
		type : "POST",
		url: "/insertChat",
		data: data,
		success: function(message) {
			console.log(message);
		}
	}) 
}

function insertWithImage() {
	var cuno = $("#cu_no").val();
	var crn = $("#cr_no").val();
	var ms = $("#msg").val();
	
	var formData=new FormData();
	var inputFile=$("#image_message");
	var files=inputFile[0].files;
	
	formData.append("uploadFile",files[0]);
	formData.append("cu_no",cuno);
	formData.append("cr_no",crn);
	formData.append("ch_content",ms);
	console.log(formData);
	
	$.ajax({
			url:"/insertChatWithImage",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			success:function(){
				console.log("이미지 포함 채팅 업로드 완료");
			}, async: false
	})
}

function send() {
	var sendimg=$("#image_message").val();
	
	if ($("#msg").val() == "" && sendimg == "") {			// 메시지와 사진 모두 비어있음
	} else if ($("#msg").val() != "" && sendimg == "") {	// 메시지만 있고 사진은 없음
		insert();
		data.cu_id = getId('cu_id').value;
		data.msg = msg.value;
		data.date = new Date().toLocaleString();
		data.type = 0;
		var temp = JSON.stringify(data);
		console.log(temp);
		ws.send(temp);
		
	} else  {
		insertWithImage();
		data.cu_id = getId('cu_id').value;
		data.msg = msg.value;
		data.date = new Date().toLocaleString();
		data.type = 1;
		var temp = JSON.stringify(data);
		console.log(temp);
		ws.send(temp);
	}
	
	$("#image_message").val(""); // 파일 전송창 지우기
	msg.value = ''; // 메시지 텍스트창 비우기
}