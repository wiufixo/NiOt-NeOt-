/**
 * web socket
 */

function getId(id) {
	return document.getElementById(id);
}

var data = {};//전송 데이터(JSON)

var ws;
var mid = getId('mid');
var btnLogin = getId('btnLogin');
var btnSend = getId('btnSend');
var talk = getId('talk');
var msg = getId('msg');

btnLogin.onclick = function() {
	var data = JSON.parse(msg.data);
	var css;

	if (data.mid == mid.value) {
		css = 'class=me';
	} else {
		css = 'class=other';
	}

	var item = `<div ${css} >
		                <span><b>${data.mid}</b></span> [ ${data.date} ]<br/>
                      <span>${data.msg}</span>
						</div>`;

	talk.innerHTML += item;
	talk.scrollTop = talk.scrollHeight;//스크롤바 하단으로 이동
}

msg.onkeyup = function(ev) {
	if (ev.keyCode == 13) {
		send();
	}
}

btnSend.onclick = function() {
	send();
}

function send2() {
	
}

function send() {
	if (msg.value.trim() != '') {
		data.mid = getId('mid').value;
		console.log(data.mid);
		data.msg = msg.value;
		data.date = new Date().toLocaleString();
		var temp = JSON.stringify(data);
		ws.send(temp);
	}


	var data = JSON.parse(msg.data);
	var css;

	if (data.mid == mid.value) {
		css = 'class=me';
	} else {
		css = 'class=other';
	}

	var item = `<div ${css} >
		                <span><b>${data.mid}</b></span> [ ${data.date} ]<br/>
                      <span>${data.msg}</span>
						</div>`;

	talk.innerHTML += item;
	talk.scrollTop = talk.scrollHeight;//스크롤바 하단으로 이동
	msg.value = '';

}

$(function(){
	
	function loadChat(){
		$("#list").empty();
		$.ajax({
			url:"/listChat",
			success:function(data){
				$.each(data, function(){
					var name = this.name;
					var message = this.message;
					var p = $("<p></p>").html( name + "===>" + message );
					$("#list").append(p);
				});		
			}
		});
	}
	
	
	$("#btnSend").click(function(){
		var data = $("#f").serializeArray();
		$.ajax({
			url:"/insertChat",
			method:"post",
			data:data,
			success:function(){
				
			}
		})
	});
	
	
	setInterval(loadChat, 1000);
	
	
})