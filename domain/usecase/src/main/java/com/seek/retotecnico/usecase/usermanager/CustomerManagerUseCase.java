package com.seek.retotecnico.usecase.usermanager;
import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.gateway.CustomerGateway;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CustomerManagerUseCase {

    private final CustomerGateway customerGateway;
    public Mono<Customer> findCustomerByDocumentId(Customer customer) {
        return customerGateway.findCustomerByDocumentId(customer.getDocumentId());
    }
    public Mono<Customer> saveCustomer(Customer customer){
        return customerGateway.createCustomer(customer);
    }
    public Mono<CustomerMetrics> getCustomerMetrics(){
        return customerGateway.getCustomerMetrics();
    }
    public Mono<List<Customer>> listAllCustomers(){
        return customerGateway.listAllCustomers();
    }

}

