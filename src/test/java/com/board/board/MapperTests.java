package com.board.board;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTests {

    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void testOfInsert(){
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setTitle("1번 게시글 제목");
        boardDTO.setContent("1번 게시글 내용");
        boardDTO.setWriter("테스터");

        int result = boardMapper.insertBoard(boardDTO);
        System.out.println("결과 데이터는 " + result);
    }

    @Test
    public void testOfSelectDetail() {
        BoardDTO boardDTO = boardMapper.selectBoardDetail((long) 1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            String boardJson = objectMapper.writeValueAsString(boardDTO);
            //Jackson 라이브러리를 통해 JSON 문자열로 변경

            System.out.println("=========================================");
            System.out.println(boardJson);
            System.out.println("=========================================");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testOfUpdate() {

        BoardDTO param = new BoardDTO();

        param.setTitle("1번 게시글 제목을 수정.");
        param.setContent("1번 게시글 내용을 수정.");
        param.setWriter("임현주");
        param.setIdx((long) 1);

        int result = boardMapper.updateBoard(param);

        if(result == 1) {
            BoardDTO boardDTO = boardMapper.selectBoardDetail((long) 1);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            //LocalDateTime Jackson 직렬화 오류 관련하여 추가하였음
            //자바 8에서는 Java.time.LocalDateTime을 지원하지않아 발생하는 오류라고 함.
            //만약 자바8이 아니면 아래 코드가 달라질거임!

            try{
                //String boardJson = new ObjectMapper().writeValueAsString(boardDTO);
                String boardJson = objectMapper.writeValueAsString(boardDTO);
                System.out.println("====================");
                System.out.println(boardJson);
                System.out.println("====================");
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
    }
}
