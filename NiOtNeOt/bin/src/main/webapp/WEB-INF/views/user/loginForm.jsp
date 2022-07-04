<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="cu_id">ID:</label> <input type="text" name="username" class="form-control" placeholder="Enter id" id="cu_id">
		</div>
		<div class="form-group">
			<label for="cu_pwd">PWD:</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="cu_pwd">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=00e34125548339316c0af9949d7e464a&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_medium.png"></a>
	</form>
</div>

<%@ include file="../layout/footer.jsp"%>