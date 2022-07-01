<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<input type="hidden" value="${principal.user.cu_no }" id="cu_no">
		<div class="form-group">
			<label for="cu_id">ID:</label> 
			<input type="text" value="${principal.user.cu_id }" class="form-control" placeholder="Enter id" id="cu_id" readonly="readonly">
		</div>
		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="cu_pwd">PWD:</label> 
				<input type="password" class="form-control" placeholder="Enter password" id="cu_pwd">
			</div>
			<div class="form-group">
				<label for="cu_email">EMAIL:</label> 
				<input type="email" value="${principal.user.cu_email }" class="form-control" placeholder="Enter email" id="cu_email">
			</div>
		</c:if>
		<c:if test="${not empty principal.user.oauth }">
			<div class="form-group">
				<label for="cu_email">EMAIL:</label> 
				<input type="email" value="${principal.user.cu_email }" readonly="readonly" class="form-control" placeholder="Enter email" id="cu_email">
			</div>
		</c:if>
	</form>
	<button id="btn-update" class="btn btn-primary">회원수정</button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>