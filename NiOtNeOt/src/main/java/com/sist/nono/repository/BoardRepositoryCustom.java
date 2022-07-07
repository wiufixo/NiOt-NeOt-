//package com.sist.nono.repository;
//
//import java.util.List;
//
//import org.springframework.stereotype.Repository;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.sist.nono.model.Board;
//import com.sist.nono.model.QBoard;
//import com.sist.nono.searchCondition.BoardSearchCondition;
//
//import lombok.RequiredArgsConstructor;
//
//@Repository
//@RequiredArgsConstructor
//public class BoardRepositoryCustom {
//	private final JPAQueryFactory queryFactory;
//	
//	QBoard board = QBoard.board;
//	
//	public List<Board> searchBoard(BoardSearchCondition condition){
//		return queryFactory
//				.selectFrom(board)
//				.where(titleEq(condition.getTitle()),
//						contentEq(condition.getContent()),
//						idEq(condition.getId()))
//				.fetch();
//	
//	private BooleanExpression titleEq(String title) {
//		if(title==null) {
//			return null;
//		}
//		return board.b_title.containsIgnoreCase(title);
//	}
//	
//	private BooleanExpression contentEq(String content) {
//		if(content==null) {
//			return null;
//		}
//		return board.b_content.containsIgnoreCase(content);
//	}
//	
//	private BooleanExpression idEq(String id) {
//		if(id==null) {
//			return null;
//		}
//		return board.user.cu_id.containsIgnoreCase(id);
//	}
//
//				
//}
