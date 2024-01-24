package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.QUser;
import com.godcoder.myhome.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findByUsernameCustom(String username) {

        QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<User> users = query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();
        return users;
    }

    @Override
    public List<User> findByUsernameJdbc(String username) {
        String sql = "SELECT * FROM user WHERE username LIKE ?";
        String searchTerm = "%" + username + "%";
        List<User> users = jdbcTemplate.query(sql, new Object[]{searchTerm}, new BeanPropertyRowMapper<>(User.class));
        return users;
    }
}
