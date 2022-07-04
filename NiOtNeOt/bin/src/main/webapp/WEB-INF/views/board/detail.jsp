<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.cu_id == principal.user.cu_id }">
		<a href="/board/${board.b_no }/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br>
	<br>
	<div>
		글번호: <span id="b_no"><i>${board.b_no }</i></span> 
		작성자: <span><i>${board.user.cu_id }</i></span>
	</div>
	<hr>
	<div>
		<h3>${board.b_title }</h3>
	</div>
	<hr>
	<div>${board.b_content }</div>
	<hr>
	<div>
		<div class="card">
			<div class="card-body">
				<textarea id="comment-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button id="btn-comment-save" class="btn btn-primary">등록</button>
			</div>
		</div>
		<br>
		<div class="card">
			<div class="card-header">댓글리스트</div>
			<ul id="comment-box" class="list-group">
			<c:forEach items="${board.boardComment }" var="bc">
				<li id="comment-1" class="list-group-item d-flex justify-content-between">
					<div>${bc.bc_content }</div>
					<div class="d-flex">
						<div class="font-italic">${bc.user.cu_id } &nbsp;</div>
						<button class="badge">삭제</button>
					</div>
				</li>
			</c:forEach>
			</ul>
		</div>
	</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>