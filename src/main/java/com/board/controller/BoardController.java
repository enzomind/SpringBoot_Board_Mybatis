package com.board.controller;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/board/write")
    public String openBoardWrite(@RequestParam(value="idx", required = false) Long idx, Model model) {

        if(idx==null) { //idx값 없을 땐 리스트가져오고
            model.addAttribute("board", new BoardDTO());
        } else { //있으면 상세화면으로
            BoardDTO boardDTO = boardService.getBoardDetail(idx);
            if(boardDTO == null) { //예외처리
                return "redirect:/board/list";
            }
            model.addAttribute("board", boardDTO); //상세에 보여줄 DTO값을 말아오기
        }

        return "board/write";
    }


    @PostMapping(value = "/board/register")
    public String registerBoard(final BoardDTO params){
        try{
            boolean isRegistered = boardService.registerBoard(params);
            if(isRegistered == false) {

            }
        } catch (DataAccessException e) {

        } catch (Exception e) {

        }

        return "redirect:/board/list";
    }

    @GetMapping(value="/board/list")
    public String openBoardList(Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        model.addAttribute("boardList", boardDTOList);

        return "board/list";
    }

}
