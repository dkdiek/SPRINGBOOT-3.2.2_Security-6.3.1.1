package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    // User 테이블에 Role 테이블 join
    @JsonIgnore//json날렸을때 안나온다
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns= @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private List<Role> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //Board에 join 해놓은 값
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //fetch eager를 하면 user조회할때 board도 같이 가져온다
    @JsonIgnore//json날렸을때 안나온다
    private List<Board> boards = new ArrayList<>();

}
