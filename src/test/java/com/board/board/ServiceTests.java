package com.board.board;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class ServiceTests {

    @Autowired
    BoardService boardService;

    @Test
    public void registerBoard() {
        BoardDTO params = new BoardDTO();
        params.setTitle("Title");
        params.setContent("Content");
        params.setWriter("Writer");

        boolean register = boardService.registerBoard(params);
        System.out.println(register);
    }

    @Test
    public void testOfgetBoardList(){
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        if (CollectionUtils.isEmpty(boardDTOList) == false) {
            for (BoardDTO boardDTO : boardDTOList) {
                System.out.println("=====================================");
                System.out.println(boardDTO.getTitle());
                System.out.println(boardDTO.getContent());
                System.out.println(boardDTO.getWriter());
                System.out.println("=====================================");
            }
        }
    }

    @Test
    public void testOfgetBoardDetail() {
        BoardDTO boardDTO = boardService.getBoardDetail((long) 1);
        System.out.println("=====================================");
        System.out.println(boardDTO);
        System.out.println("=====================================");
    }

    @Test
    public void testOfdeleteBoard(){
        boolean board = boardService.deleteBoard((long) 2);
        System.out.println("=====================================");
        System.out.println(board);
        System.out.println("=====================================");
    }
}
