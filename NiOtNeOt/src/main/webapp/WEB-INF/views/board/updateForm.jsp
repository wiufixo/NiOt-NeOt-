<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container">
	<form>
		<input type="hidden" id="no" value="${board.b_no }">
		<div class="form-group">
			<label for="title">title:</label> 
			<input value="${board.b_title }" type="text" class="form-control" id="title" placeholder="Enter title">
		</div>
		<div class="form-group">
			<label for="content">content:</label>
			<textarea class="form-control summernote" rows="5" id="content">${board.b_content }</textarea>
		</div>
	</form>
	<button id="btn-update" class="btn btn-primary">수정하기</button>
</div>
<script>
	$('.summernote').summernote({
		placeholder : "Enter content",
		tabsize : 2,
		height : 300
	});
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>