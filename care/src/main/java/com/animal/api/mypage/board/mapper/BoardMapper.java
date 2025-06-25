package com.animal.api.mypage.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.mypage.board.model.response.BoardListResponseDTO;

@Mapper
public interface BoardMapper {
	
    // 내가 작성한 자유/보호소 게시글 조회 (공지 제외)
    List<BoardListResponseDTO> findWrittenBoardListByUserIdx(@Param("userIdx") int userIdx, @Param("offset") int offset);

    // 내가 좋아요한 모든 게시글 조회 (IS_LIKE = 1)
    List<BoardListResponseDTO> findLikedBoardListByUserIdx(@Param("userIdx") int userIdx, @Param("offset") int offset);

    // 내가 작성한 글 개수 조회 (공지 제외)
    int countWrittenBoardListByUserIdx(@Param("userIdx") int userIdx);

    // 내가 좋아요한 글 개수 조회
    int countLikedBoardListByUserIdx(@Param("userIdx") int userIdx);
}
