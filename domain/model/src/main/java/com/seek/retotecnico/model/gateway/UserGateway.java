package com.seek.retotecnico.model.gateway;

import com.seek.retotecnico.model.customer.User;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerGateway {
    Mono<User> createCustomer(User user);
    Mono<User> findCustomerByDocumentId(String documentId);
    Mono<CustomerMetrics> getCustomerMetrics();
    Mono<List<User>> listAllCustomers();
}
