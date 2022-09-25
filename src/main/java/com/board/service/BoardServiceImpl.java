package com.board.service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    public boolean registerBoard(BoardDTO params) {

        int queryResult = 0;

        if (params.getIdx() == null) {
            queryResult = boardMapper.insertBoard(params);
        } else {
            queryResult = boardMapper.updateBoard(params);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    @Override
    public boolean deleteBoard(Long idx) {

        int queryResult = 0;

        BoardDTO boardDTO = boardMapper.selectBoardDetail(idx);

        if (boardDTO != null && "N".equals(boardDTO.getDeleteYn())) {
            queryResult = boardMapper.deleteBoard(idx);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public List<BoardDTO> getBoardList() {

        List<BoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = boardMapper.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            boardList = boardMapper.selectBoardList();
        }

        return boardList;
    }
}
