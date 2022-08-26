package org.zerock.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository repository;

  @Override
  public Long register(ReplyDTO replyDTO) {
    Reply reply = dtoToEntity(replyDTO);
    repository.save(reply).getRno();
    return reply.getRno();
    // return repository.save(dtoToEntity(replyDTO)).getRno();
  }

  @Override
  public List<ReplyDTO> getList(Long bno) {
    List<Reply> result = repository.getRepliesByBoardOrderByRno(
                                Board.builder().bno(bno).build());
    return result.stream().map(reply->entityToDTO(reply))
                          .collect(Collectors.toList());
  }

  @Override
  public void modify(ReplyDTO replyDTO) {
    repository.save(dtoToEntity(replyDTO));
  }

  @Override
  public void remove(Long rno) {
    repository.deleteById(rno);
  }
}
