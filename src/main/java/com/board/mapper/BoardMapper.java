package com.board.mapper;

import com.board.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
//MyBatis는 인터페이스에 @Mapper를 지정해주면 XML Mapper에서 메서드의 이름과 일치하는 SQL문을 찾아 실행!
//JSP 했을때 했던걸 여기선 이렇게 적용한다라고 알고있으면 될듯.
public interface BoardMapper {

    public int insertBoard(BoardDTO params);

    public BoardDTO selectBoardDetail(Long idx);

    public int updateBoard(BoardDTO params);

    public int deleteBoard(Long idx);

    public List<BoardDTO> selectBoardList();

    public int selectBoardTotalCount();

}
