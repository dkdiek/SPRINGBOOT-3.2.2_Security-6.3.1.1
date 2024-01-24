package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable
            ,@RequestParam(required = false, defaultValue = "") String searchText){ //기본 보여줄 개수 2로 설정
        //Page<Board> boards = boardRepository.findAll(pageable); //페이징 page랑 size개수
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() -4); //시작페이지를 현재페이지-4로 표시한다, 최소값0
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() +4); //끝페이지를 현재페이지+4로 표시한다, 최대 전체 페이지수
        //클라이언트에 페이징 정보 전달
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("board",new Board());
        } else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){ //시큐리티에서 인증정보를 받는다
        boardValidator.validate(board, bindingResult);//만든 validation을 가져온다 content 빈 값 불가하도록 유효성 검사
        
        if (bindingResult.hasErrors()){
            return "board/form";
        }

        //시큐리티에서 userName을 받아 업데이트
        String userName = authentication.getName();
        boardService.save(userName, board);
        return "redirect:/board/list";
    }

}
