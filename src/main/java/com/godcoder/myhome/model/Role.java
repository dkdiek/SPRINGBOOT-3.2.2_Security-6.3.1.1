package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Role 테이블에서도 User 테이블 조인 활용하여 조인(양방향 맵핑)
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

}
