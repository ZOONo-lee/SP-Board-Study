package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {
  Long register(BoardDTO dto);
  PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO);
  BoardDTO get(Long bno);
  void removeWithReplies(Long bno);
  void modify(BoardDTO dto);

  default Board dtoToEntity(BoardDTO dto){
    Member member = Member.builder().email(dto.getWriterEmail()).build();
    Board board = Board.builder().bno(dto.getBno()).title(dto.getTitle())
                    .content(dto.getContent()).writer(member).build();
    return board;
  }
  default BoardDTO entityToDTO(Board b, Member m, Long replyCount){
    BoardDTO dto = BoardDTO.builder().bno(b.getBno()).title(b.getTitle())
                    .content(b.getContent()).regDate(b.getRegDate())
                    .modDate(b.getModDate()).writerEmail(m.getEmail())
                    .writerName(m.getName()).replyCount(replyCount.intValue())
                    .build();
    return dto;
  }
}
