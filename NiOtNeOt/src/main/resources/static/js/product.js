
var transExpantionPaging = function(){
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




$(document).on("click","#",function(){
	transExpantionPaging($(this).text());
})
