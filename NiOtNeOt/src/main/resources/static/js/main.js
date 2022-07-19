$(function(){
    
    
});


function isOnScreen(elem) {
	// if the element doesn't exist, abort
	if(elem.length == 0) {
		return;
	}
	var $window = $(window),
        viewport_top = $window.scrollTop(),
        viewport_height = $window.height(),
        viewport_bottom = viewport_top + viewport_height,
        $elem = $(elem),
        top = $elem.offset().top,
        height = $elem.height(),
        bottom = top + height;

	return (top >= viewport_top && top < viewport_bottom) ||
	(bottom > viewport_top && bottom <= viewport_bottom) ||
	(height > viewport_height && top <= viewport_top && bottom >= viewport_bottom)
}
var this_scroll, w_height_half, conceal;
$(window).load(function(){    
    $(window).scroll(function(){
        this_scroll = $(window).scrollTop();
        $(".conceal").each(function(){
            if(isOnScreen($(this))){
                if($(this).offset().top - $(window).height()/1.75 < this_scroll){
                    $(this).removeClass("conceal");                  
                }
            }
        });        
    });
});

