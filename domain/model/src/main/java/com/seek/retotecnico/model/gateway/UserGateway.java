package com.seek.retotecnico.model.gateway;

import com.seek.retotecnico.model.user.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserGateway {
    Mono<User> findUser(User user);
    Mono<User> createUser(User user);
    Mono<User> findUserByEmailAndPassword(String email, String password);
}
