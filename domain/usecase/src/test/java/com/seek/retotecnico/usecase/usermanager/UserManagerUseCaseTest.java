package com.seek.retotecnico.usecase.usermanager;

import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserManagerUseCaseTest {

    @Mock
    private UserGateway userGateway;

    private UserManagerUseCase userManagerUseCase;

    private Mono<User> processClient;
    private User processUserCrud;
    @BeforeEach
    public void setUp() {
        userManagerUseCase = new UserManagerUseCase(userGateway);
        processClient = buildMonoClient();
        processUserCrud = buildClient();

    }

    @Test
    void shouldUserAlreadyRegister() {

        given(userGateway.createCustomer(any())).willReturn(processClient);

        Mono<User> response = userManagerUseCase.saveCustomer(
                processUserCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("Peter", client.getName());
                }).verifyComplete();

        verify(userGateway, times(1)).createCustomer(any());
    }

    @Test
    void shouldUserRegisterSuccess() {

        given(userGateway.createCustomer(any())).willReturn(processClient);

        Mono<User> response = userManagerUseCase.saveCustomer(
                processUserCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("Peter", client.getName());
                }).verifyComplete();

        verify(userGateway, times(1)).createCustomer(any());
    }

    private Mono<User> buildMonoClient(){
        return Mono.just(User.builder()
                .name("Peter")
                .lastName("Isla")
                .birthDay(new Date())
                .build());
    }

    private User buildClient(){
        return User.builder()
                .name("Peter")
                .lastName("Isla")
                .birthDay(new Date())
                .build();
    }

}
