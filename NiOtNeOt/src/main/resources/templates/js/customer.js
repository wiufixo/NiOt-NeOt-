		$(document).ready(function() {
			$("#find_pwd").on("click",function(){
				var cu_id=$("#cu_id").val();
				$.ajax({
					url:"/customer/findByCu_id",
					method:"post",
					data:{
						cu_id:cu_id
					},
					dataType="json",
					success:function(data){
						console.log(data);
						if(data.cu_no!=0){
							window.location.href="";
							//email test 후 비밀번호 전송
						}else if(data.cu_no==0){
							//hidden된 메시지 출력
						}
					}
				});	
			})
		});