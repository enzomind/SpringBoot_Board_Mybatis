package com.board.controller;

import com.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/board/write")
    public String openBoardWrite(Model model) {

        String title="제목 파람";
        String content="테스트 내용 파람";
        String writer="작성테스터 파람";

        model.addAttribute("titleParam", title);
        model.addAttribute("contentParam", content);
        model.addAttribute("writerParam", writer);

        return "board/write";
    }

}
