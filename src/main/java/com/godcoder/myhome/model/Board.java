package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull //유효성 검사 form.html에서 조건을 충족하지 않으면 에러 메시지 나오게하였음
    @Size(min=2, max=30, message = "제목은 2자이상 30자이하여야 합니다.")
    private String title;
    
    private String content;

    @ManyToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "id") //member테이블에 id가 pk이어서 refcolname 생략도 가능
    @JsonIgnore
    private User user;

}
