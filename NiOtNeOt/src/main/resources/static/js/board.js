isEmpty = function(str){
	return (str == '' || str == undefined || str == null || str == 'null');
}

isNotEmpty = function(str){
	return !isEmpty(str)
}



/*openModal=function(bc_no, cu_id, bc_content){
	$("#commentModal").modal("toggle");

		document.getElementById("modalWriter").value = cu_id;
		document.getElementById("modalContent").value = bc_content;

<<<<<<< HEAD
		document.getElementById("btnCommentUpdate").setAttribute("onclick", "updateComment("+ bc_no +")");
}*/




