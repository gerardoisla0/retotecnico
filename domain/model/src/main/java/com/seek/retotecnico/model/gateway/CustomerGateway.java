package com.seek.retotecnico.model.gateway;

import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerGateway {
    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> findCustomerByDocumentId(String documentId);
    Mono<CustomerMetrics> getCustomerMetrics();
    Mono<List<Customer>> listAllCustomers();
}
