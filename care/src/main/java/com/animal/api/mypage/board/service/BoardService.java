package com.animal.api.mypage.board.service;

import com.animal.api.mypage.board.model.response.BoardPageResponseDTO;

public interface BoardService {

	BoardPageResponseDTO getWrittenBoardListByUserIdx(int userIdx, int page);

	BoardPageResponseDTO getLikedBoardListByUserIdx(int userIdx, int page);
}
