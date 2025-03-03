package com.seek.retotecnico.usecase.usermanager;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.gateway.UserGateway;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CustomerManagerUseCase {
    private final UserGateway userGateway;
    public Mono<User> saveUser(User user){
        return userGateway.createUser(user);
    }
    public Mono<User> loginUser(String email, String password){
        return userGateway.loginUser(email,password);
    }

}

