package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceImplTests {
  @Autowired
  private BoardService service;

  @Test
  void testRegister() {
    BoardDTO dto = BoardDTO.builder().title("Test title...")
                    .content("Test Content...")
                    .writerEmail("user55@aaa.com")
                    .build();
    Long bno = service.register(dto);
    log.info("service testRegister:"+bno);
  }

  @Test
  public void testList(){
    PageRequestDTO requestDTO = new PageRequestDTO();
    PageResultDTO<BoardDTO, Object[]> result = service.getList(requestDTO);
    for(BoardDTO dto : result.getDtoList()) System.out.println(dto);
  }

  @Test
  public void testGet(){
    System.out.println(service.get(93L));
  }
  @Test
  public void testRemove(){
    service.removeWithReplies(101L);
  }

  @Test
  public void testModify(){
    BoardDTO dto = BoardDTO.builder().bno(100L)
                    .title("제목 변경함").content("내용변경함")
                    .build();
    service.modify(dto);
  }
  
}
