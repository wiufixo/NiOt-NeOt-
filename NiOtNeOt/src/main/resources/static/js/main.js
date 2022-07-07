$(function() {
	var product_list_length = $(".product_list_con ul.product_list li").length;
	product_list_length = product_list_length * $(".product_list_con ul.product_list li").outerWidth();
	$(".product_list").width(product_list_length);

	var this_x;
	$(".product_list_con").mousedown(function(e) {
		$(this).addClass("scrolling_ready").blur();
		this_x = e.pageX;
	}).mouseup(function() {
		$(this).removeClass("scrolling_ready").blur();
	}).hover(function() {
	}, function() {
		$(this).removeClass("scrolling_ready").blur();
	});

	$(document).on("mousemove", ".product_list_con.scrolling_ready", function(f) {
		f.preventDefault();
		$(".product_list_con").blur();
		if (this_x > f.pageX) {
			$(".product_list_con").scrollLeft($(".product_list_con").scrollLeft() + 20);
		} else if (this_x < f.pageX) {
			$(".product_list_con").scrollLeft($(".product_list_con").scrollLeft() - 20);
		}
		this_x = f.pageX;
	});

});


function isOnScreen(elem) {
	// if the element doesn't exist, abort
	if (elem.length == 0) {
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
$(window).load(function() {
	$(window).scroll(function() {
		this_scroll = $(window).scrollTop();
		$(".conceal").each(function() {
			if (isOnScreen($(this))) {
				if ($(this).offset().top - $(window).height() / 1.75 < this_scroll) {
					$(this).removeClass("conceal");
				}
			}
		});
	});
});

$(function() {
	$(".chat").click(function openWindowPop(url, name){
        var options = 'top=100, left=650, width=800, height=815, status=no, menubar=no, toolbar=no, resizable=no';
        window.open(url, name, options);
    });
})

