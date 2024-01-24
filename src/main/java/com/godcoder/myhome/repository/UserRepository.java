package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomizedUserRepository {
    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();

    User findByUsername(String username);

    //JPQL 앞뒤 like
    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    //Native sql 쿼리문
    @Query(value = "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username);
}
