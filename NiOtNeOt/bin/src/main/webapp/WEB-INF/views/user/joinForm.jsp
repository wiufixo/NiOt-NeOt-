<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
		<div class="form-group">
			<label for="cu_id">ID:</label> <input type="text" class="form-control" placeholder="Enter id" id="cu_id">
		</div>
		<div class="form-group">
			<label for="cu_pwd">PWD:</label> <input type="password" class="form-control" placeholder="Enter password" id="cu_pwd">
		</div>
		<div class="form-group">
			<label for="cu_email">EMAIL:</label> <input type="email" class="form-control" placeholder="Enter email" id="cu_email">
		</div>
	</form>
	<button id="btn-save" class="btn btn-primary">회원가입</button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>