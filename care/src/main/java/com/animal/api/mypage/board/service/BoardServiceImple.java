package com.animal.api.mypage.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.mypage.board.mapper.BoardMapper;
import com.animal.api.mypage.board.model.response.BoardListResponseDTO;
import com.animal.api.mypage.board.model.response.BoardPageResponseDTO;

@Service
@Primary
public class BoardServiceImple implements BoardService {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public BoardPageResponseDTO getWrittenBoardListByUserIdx(int userIdx, int page) {
        int offset = (page - 1) * PAGE_SIZE; // 5개씩 오프셋 계산
        int total = boardMapper.countWrittenBoardListByUserIdx(userIdx); // 총 개수 조회
        List<BoardListResponseDTO> list = boardMapper.findWrittenBoardListByUserIdx(userIdx, offset);  // 목록 조회
        return new BoardPageResponseDTO(total, list); // 총 개수 + 목록 DTO 반환
    }

    @Override
    public BoardPageResponseDTO getLikedBoardListByUserIdx(int userIdx, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        int total = boardMapper.countLikedBoardListByUserIdx(userIdx);
        List<BoardListResponseDTO> list = boardMapper.findLikedBoardListByUserIdx(userIdx, offset);
        return new BoardPageResponseDTO(total, list);
    }
}
