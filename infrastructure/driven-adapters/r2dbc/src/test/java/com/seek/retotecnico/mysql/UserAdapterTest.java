package com.seek.retotecnico.mysql;

import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.mysql.model.UserData;
import com.seek.retotecnico.mysql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @InjectMocks
    private UserAdapter customerAdapter;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldRegisterClient() {
        when(userRepository.save(any())).thenReturn(buildMonoClientData());

        Mono<User> result = customerAdapter.createCustomer(buildClient());

        StepVerifier.create(result)
                .expectNext(buildClient())
                .verifyComplete();
    }
    @Test
    void shouldFindClient() {
        when(userRepository.findByDocumentId(anyString())).thenReturn(buildMonoClientData());
        Mono<User> result = customerAdapter.findCustomerByDocumentId("123456");

        StepVerifier.create(result)
                .expectNext(buildClient())
                .verifyComplete();
    }

    @Test
    void shouldFindNotClient() {
        when(userRepository.findByDocumentId(anyString())).thenReturn(
                Mono.empty());
        Mono<User> signatureDetailSaved = customerAdapter.findCustomerByDocumentId("12344");

        StepVerifier.create(signatureDetailSaved)
                .verifyComplete();
    }

    private Mono<UserData> buildMonoClientData(){
        return Mono.just(UserData.builder()
                .name("Peter")
                .lastName("isla")
                .documentId("123456")
                .birthDay(null)
                .age(33)
                .build());
    }

    private User buildClient(){
        return User.builder()
                .name("Peter")
                .lastName("isla")
                .documentId("123456")
                .birthDay(null)
                .age(33)
                .build();
    }

}
