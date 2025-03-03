package com.seek.retotecnico.usecase.usermanager;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserManagerUseCase {

    private final UserGateway userGateway;

    public Mono<User> findUser(User user){
        return userGateway.findUser(user);
    }
    public Mono<User> saveUser(User user){
        return userGateway.createUser(user);
    }
    public Mono<User> loginUser(String email, String password){
        return userGateway.findUserByEmailAndPassword(email,password);
    }

}

