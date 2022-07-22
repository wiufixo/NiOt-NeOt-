$(function(){
    $(".btn_gotop").click(function(){
        $("html, body").stop().animate({scrollTop:0}, 750,function(){});
    });
    
    $(".btn_nav").click(function(){
        if($(".header").hasClass("nav_open")){
            $(".header").removeClass("nav_open");
        }else{
            $(".header").addClass("nav_open");
        }
    });
    
    $(".blurred_cover").click(function(){
        $(".header").removeClass("nav_open");
    });
    
    var old_scroll = 0, new_scroll = 0;
    $(window).scroll(function(){
        $(".header").removeClass("nav_open");
        new_scroll = $(window).scrollTop();
        if(old_scroll < new_scroll && new_scroll > 0){
            $(".header").addClass("hide");
        }else{
            $(".header").removeClass("hide");
        }
        old_scroll = new_scroll
        
        if(0 < new_scroll){
            $(".btn_gotop").removeClass("hide");
        }else{
           $(".btn_gotop").addClass("hide");
        }
    });
});
    
    $(".chat").click(function(){
		console.log("작동");
		if ($("#authname").val() == null || $("#authname").val() == "anonymousUser") {
			window.location.href="/customer/login"
		} else {
        	$(".chatting").toggleClass("active");
		}
	});
	
	$(".alerts").click(function(){
		console.log("작동");
		if ($("#authname").val() == null || $("#authname").val() == "anonymousUser") {
			window.location.href="/customer/login"
		} else {
        	$(".alertss").toggleClass("active");
		}
	});
    
$(window).load(function(){
    $("html, body").animate({scrollTop: 0}, 1, function(){});
    setTimeout(function(){
        $(".header").removeClass("hide");
    }, 750);
    setTimeout(function(){
        $(".delay").removeClass("delay");
    }, 1000);
    $("body").removeClass("loading");
    
});