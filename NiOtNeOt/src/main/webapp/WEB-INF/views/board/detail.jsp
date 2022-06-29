<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<div>
		글번호: <span id="b_no"><i>${board.b_no }</i></span> 작성자: <span><i>${board.user.cu_id }</i></span>
	</div>
	<hr>
	<div>
		<h3>${board.b_title }</h3>
	</div>
	<hr>
	<div>${board.b_content }</div>
	<hr>
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.cu_id == principal.user.cu_id }">
		<a href="/board/${board.b_no }/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>