$(document).ready(function(){
	$("#login").on("click",function(){
		$("#loginForm").css({
			"display":"block"
		})
	})
	
	$("#x").on("click",function(){
		$("#loginForm").css({
			"display":"none"
		})
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