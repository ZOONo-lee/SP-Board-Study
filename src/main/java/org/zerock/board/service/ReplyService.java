package org.zerock.board.service;

import java.util.List;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

public interface ReplyService {
  Long register(ReplyDTO replyDTO);
  List<ReplyDTO> getList(Long bno);
  void modify(ReplyDTO replyDTO);
  void remove(Long rno);

  default Reply dtoToEntity(ReplyDTO dto){
    Board board = Board.builder().bno(dto.getBno()).build();
    Reply reply = Reply.builder().rno(dto.getRno())
                  .text(dto.getText()).replyer(dto.getReplyer())
                  .board(board).build();
    return reply;
  }
  default ReplyDTO entityToDTO(Reply reply){
    ReplyDTO replyDTO = ReplyDTO.builder().rno(reply.getRno())
                  .text(reply.getText()).replyer(reply.getReplyer())
                  .regDate(reply.getRegDate())
                  .modDate(reply.getModDate()).build();
    return replyDTO;
  }
  
}
