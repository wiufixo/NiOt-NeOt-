package com.sist.nono.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sist.nono.dto.BoardDto;
import com.sist.nono.paging.CommonParams;

@Repository
@Mapper
public interface BoardDao {
	/**
     * 게시글 수 조회
     */
    int count(CommonParams params);
    int countComment(CommonParams params);
    

    /**
     * 게시글 리스트 조회
     */
    List<BoardDto> findAll(final CommonParams params);

    
    List<BoardDto> findComment(CommonParams params);
    
    
}
