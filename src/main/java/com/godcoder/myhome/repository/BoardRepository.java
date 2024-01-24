package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation

    List<Board> findByTitle(String title); //제목 조회 추가
    List<Board> findByTitleOrContent(String title, String Content); //제목,내용 조회 추가 or조건으로 title또는 Content , and도 가능
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable); //제목, 내용 검색기능 추가

}
