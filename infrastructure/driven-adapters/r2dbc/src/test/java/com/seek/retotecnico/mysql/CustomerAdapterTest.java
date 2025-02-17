package com.seek.retotecnico.mysql;

import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.mysql.model.CustomerData;
import com.seek.retotecnico.mysql.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAdapterTest {

    @InjectMocks
    private CustomerAdapter customerAdapter;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void shouldRegisterClient() {
        when(customerRepository.save(any())).thenReturn(buildMonoClientData());

        Mono<Customer> result = customerAdapter.createCustomer(buildClient());

        StepVerifier.create(result)
                .expectNext(buildClient())
                .verifyComplete();
    }
    @Test
    void shouldFindClient() {
        when(customerRepository.findByDocumentId(anyString())).thenReturn(buildMonoClientData());
        Mono<Customer> result = customerAdapter.findCustomerByDocumentId("123456");

        StepVerifier.create(result)
                .expectNext(buildClient())
                .verifyComplete();
    }

    @Test
    void shouldFindNotClient() {
        when(customerRepository.findByDocumentId(anyString())).thenReturn(
                Mono.empty());
        Mono<Customer> signatureDetailSaved = customerAdapter.findCustomerByDocumentId("12344");

        StepVerifier.create(signatureDetailSaved)
                .verifyComplete();
    }

    private Mono<CustomerData> buildMonoClientData(){
        return Mono.just(CustomerData.builder()
                .name("Peter")
                .lastName("isla")
                .documentId("123456")
                .birthDay(null)
                .age(33)
                .build());
    }

    private Customer buildClient(){
        return Customer.builder()
                .name("Peter")
                .lastName("isla")
                .documentId("123456")
                .birthDay(null)
                .age(33)
                .build();
    }

}
