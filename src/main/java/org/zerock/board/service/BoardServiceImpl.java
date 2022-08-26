package org.zerock.board.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;
import org.zerock.board.repository.search.SearchBoardRepository;
import org.zerock.board.repository.search.SearchBoardRepositoryImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
  private final BoardRepository repository;
  private final ReplyRepository replyRepository;

  @Override
  public Long register(BoardDTO dto) {
    log.info("register..."+dto);
    Board board = dtoToEntity(dto);
    repository.save(board);

    return board.getBno();
  }
  @Override
  public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO) {
    log.info("BoardServiceImpl getList..." + requestDTO);
    // Page<Object[]> result = repository.getBoardWithReplyCount(
    //   requestDTO.getPageable(Sort.by("bno").descending()));

    //Page객체 획득 
    // =>목록의 아이템리스트를 알수 있음.You can see the list of items in the list.
    Page<Object[]> result = repository.searchPage(
      requestDTO.getType(),
      requestDTO.getKeyword(),
      requestDTO.getPageable(Sort.by("bno").descending()));
    
    Function<Object[], BoardDTO> fn = 
        en -> entityToDTO((Board)en[0],(Member)en[1], (Long)en[2]);
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public BoardDTO get(Long bno) {
    Object result = repository.getBoardByBno(bno);
    Object[] arr = (Object[])result;
    return entityToDTO((Board) arr[0],(Member)arr[1],(Long)arr[2]);
  }

  @Transactional //board, reply를 삭제(by bno)
  @Override
  public void removeWithReplies(Long bno) {
    replyRepository.deleteByBno(bno);  //Reply
    repository.deleteById(bno);  //Board
  }
  @Override
  public void modify(BoardDTO dto) {
    Optional<Board> result = repository.findById(dto.getBno());
    if(result.isPresent()){
      log.info("modify..........." + dto.getBno());
      Board board = result.get();
      board.changeTitle(dto.getTitle());
      board.changeContent(dto.getContent());
      repository.save(board);
    }
  }
}
