function getId(id) {
	return document.getElementById(id);
}

var starttrade3 = getId('starttrade2');

starttrade3.onclick = (function() {	// 전송 버튼으로 전송 가능
	var pr_no = $("#productNo").val();
	
	var data = {
		"pr_no": pr_no
	};
	
	console.log(data);
	$.ajax({            
		type : "POST",
		url: "/createChatRoom",
		data: data,
		dataType: 'json',
		success: function() {
			console.log("asd");
		}
		
	}) 
	
	setTimeout(function() {
		
	$(".chatting").toggleClass("active");
	$('.chatframe').attr('src', '/listChatRoom');
	}, 2000);
	
})