package com.seek.retotecnico.usecase.usermanager;

import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.gateway.CustomerGateway;
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
class CustomerManagerUseCaseTest {

    @Mock
    private CustomerGateway customerGateway;

    private CustomerManagerUseCase customerManagerUseCase;

    private Mono<Customer> processClient;
    private Customer processCustomerCrud;
    @BeforeEach
    public void setUp() {
        customerManagerUseCase = new CustomerManagerUseCase(customerGateway);
        processClient = buildMonoClient();
        processCustomerCrud = buildClient();

    }

    @Test
    void shouldUserAlreadyRegister() {

        given(customerGateway.createCustomer(any())).willReturn(processClient);

        Mono<Customer> response = customerManagerUseCase.saveCustomer(
                processCustomerCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("Peter", client.getName());
                }).verifyComplete();

        verify(customerGateway, times(1)).createCustomer(any());
    }

    @Test
    void shouldUserRegisterSuccess() {

        given(customerGateway.createCustomer(any())).willReturn(processClient);

        Mono<Customer> response = customerManagerUseCase.saveCustomer(
                processCustomerCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("Peter", client.getName());
                }).verifyComplete();

        verify(customerGateway, times(1)).createCustomer(any());
    }

    private Mono<Customer> buildMonoClient(){
        return Mono.just(com.seek.retotecnico.model.customer.Customer.builder()
                .name("Peter")
                .lastName("Isla")
                .birthDay(new Date())
                .build());
    }

    private Customer buildClient(){
        return com.seek.retotecnico.model.customer.Customer.builder()
                .name("Peter")
                .lastName("Isla")
                .birthDay(new Date())
                .build();
    }

}
