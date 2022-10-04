package com.board.controller;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.UiUtils;
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
public class BoardController extends UiUtils {

    private final BoardService boardService;


    @PostMapping(value="/board/delete")
    public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx, Model model) {

        if(idx == null) {
            return showMessageWithRedirect("잘못된 접급입니다.", "/board/list", Method.GET, null, model);
        }

        try {
            boolean isDeleted = boardService.deleteBoard(idx);
            if(isDeleted == false) {
                //게시글 삭제 실패 시 오류 캐치
                return showMessageWithRedirect("게시글 삭제에 실패했습니다.", "/board/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {
            //DB 처리 과정 시 오류 캐치
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
        } catch (Exception e) {
            //나머지 익셉션 에러 캐치
            return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);
        }

        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list", Method.GET, null, model);
    }


    @GetMapping(value = "/board/view")
    public String openBoardDetail(@RequestParam(value="idx", required = false) Long idx, Model model) {

        if(idx==null) {
            return "redirect:/board/list";
        }

        BoardDTO boardDTO = boardService.getBoardDetail(idx);
        if(boardDTO==null || "Y".equals(boardDTO.getDeleteYn())) {

            return "redirect:/board/list";
        }
        model.addAttribute("board", boardDTO);

        return "board/view";
    }


    @GetMapping(value = "/board/write")
    public String openBoardWrite(@RequestParam(value="idx", required = false) Long idx, Model model) {

        //작성과 수정을 함께 처리

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
    public String registerBoard(final BoardDTO params, Model model){
        try{
            boolean isRegistered = boardService.registerBoard(params);
            if(isRegistered == false) {
                return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list", Method.GET, null, model);
            }
        } catch (DataAccessException e) {

            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/board/list", Method.GET, null, model);

        } catch (Exception e) {

            return showMessageWithRedirect("시스템에 문제가 발생하였습니다", "board/list", Method.GET, null, model);
        }

//        return "redirect:/board/list";
        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list", Method.GET, null, model);
    }

    @GetMapping(value="/board/list")
    public String openBoardList(Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        model.addAttribute("boardList", boardDTOList);

        return "board/list";
    }

}
