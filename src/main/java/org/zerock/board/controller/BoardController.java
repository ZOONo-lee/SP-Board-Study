package org.zerock.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("board")
@Log4j2
@RequiredArgsConstructor

public class BoardController {
  private final BoardService service;

  @GetMapping({"/",""})
  public String index(){
    log.info("index...........");
    return "redirect:/board/list";
  }

  @GetMapping("list")
  public void list(Model model, @ModelAttribute("requestDTO")PageRequestDTO req) {
    log.info("list...... " + req);
    model.addAttribute("result", service.getList(req));
  }

  @GetMapping("/register")
  public void register(){log.info("register...");}

  @PostMapping("/register")
  public String registerPost(BoardDTO dto, RedirectAttributes ra){
    log.info("register Post ..." + dto);
    Long bno = service.register(dto);
    ra.addFlashAttribute("msg",bno + " 등록");
    return "redirect:/board/list";
  }
  @GetMapping({"read","modify"})
  public void read(Long bno, Model model,
                    @ModelAttribute("requestDTO") PageRequestDTO req){
    log.info("read... bno:"+bno);
    BoardDTO dto = service.get(bno);
    model.addAttribute("dto", dto);
  }
  @PostMapping("/remove")
  public String remove(long bno, RedirectAttributes ra,
                        PageRequestDTO requestDTO ){
    log.info("remove..."+bno);
    service.removeWithReplies(bno);
    ra.addFlashAttribute("msg", bno+ " 삭제");
    ra.addAttribute("page", requestDTO.getPage());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    return "redirect:/board/list";
  }

  @PostMapping("/modify")
  public String modifyPost(BoardDTO dto, RedirectAttributes ra,
    PageRequestDTO requestDTO ){
    log.info("modifyPost..."+dto);
    service.modify(dto);
    ra.addFlashAttribute("msg", dto.getBno()+" 수정");
    ra.addAttribute("bno", dto.getBno());
    ra.addAttribute("page", requestDTO.getPage());
    ra.addAttribute("type", requestDTO.getType());
    ra.addAttribute("keyword", requestDTO.getKeyword());
    return "redirect:/board/read";
  }

}
