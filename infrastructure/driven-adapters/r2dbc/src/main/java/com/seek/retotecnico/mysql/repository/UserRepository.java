package com.seek.retotecnico.mysql.repository;

import com.seek.retotecnico.mysql.model.UserData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserData, String>, ReactiveQueryByExampleExecutor<UserData> {
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    Mono<UserData> findUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    @Query("SELECT * FROM user WHERE email = :email")
    Mono<UserData> findUserByEmail(@Param("email") String email);
}