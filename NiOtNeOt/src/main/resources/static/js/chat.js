
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

				var item = `<div ${css} >
		                <span><b>${data[index].customer.cu_id}</b></span> [ ${data[index].ch_created} ]<br/>
                     	<span>${data[index].ch_content}</span>
						</div>`;

				talk.innerHTML += item;
				talk.scrollTop = talk.scrollHeight;//스크롤바 하단으로 이동
			});
		}
	});
})


ws.onmessage = function(msg) {
	var data = JSON.parse(msg.data);
	var css;
	if (data.cu_id == cu_id.value) { // 자신 아이디인지 상대 아이디인지에 따라 적용되는 CSS를 달리 함
		css = 'class=me';
	} else {
		css = 'class=other';
	}

	var item = `<div ${css} >
			                <span><b>${data.cu_id}</b></span> [ ${data.date} ]<br/>
	                      <span>${data.msg}</span>
							</div>`;

	talk.innerHTML += item;
	talk.scrollTop = talk.scrollHeight; //스크롤바 하단으로 이동
}

msg.onkeyup = function(ev) {	// 엔터 키로 전송 가능
	if (ev.keyCode == 13) {
		send();
	}
}

btnSend.onclick = function() {	// 전송 버튼으로 전송 가능
	send();
}

function insert() {
	console.log("send 버튼 작동");
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

function send() {
	insert();

	if (msg.value.trim() != '') {
		data.cu_id = getId('cu_id').value;
		data.msg = msg.value;
		data.date = new Date().toLocaleString();
		var temp = JSON.stringify(data);
		console.log(temp);
		ws.send(temp);
	}

	msg.value = ''; // 메시지 텍스트창 비우기
}