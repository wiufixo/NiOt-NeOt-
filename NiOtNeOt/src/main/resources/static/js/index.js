$(document).ready(function(){
	$("#login").on("click",function(){
		window.location.href="/customer/login";
	})
	
	$("#join").on("click",function(){
		window.location.href="/customer/join";
	})
	
	$("#findPwd").on("click",function(){
		window.location.href="/customer/findPwd";
	})
	
	$("#logout").on("click",function(){
		location.href="/logout";
	})
})