package com.board.board;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
