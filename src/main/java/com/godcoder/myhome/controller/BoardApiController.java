package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestScope;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;
    
    //조회
   /* @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false) String title) { //required true로 하면 title값 없으면 error, 기본 값 ""
        if(StringUtils.isEmpty(title)){
            return repository.findAll(); //title값이 전달안되면 전체 조회
        } else {
            return repository.findByTitle(title); //title값이 전달되면 해당 title명으로 조회
        }
    }*/
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title
        ,@RequestParam(required = false, defaultValue="") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll(); //title, content 값이 전달안되면 전체 조회
        } else {
            return repository.findByTitleOrContent(title, content); //title값이 전달되면 해당 title명으로 조회
        }
    }
    
    //인서트
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // id값으로 1개 조회
    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id)
                .orElse(null); //없을 경우 null return
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @Secured("ROLE_ADMIN") // MehtodSecurityConfig 설정하여 ROLE_ADMIN만 삭제 가능 postman등으로 불가
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

