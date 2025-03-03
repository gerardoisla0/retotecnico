package com.seek.retotecnico.mysql.repository;

import com.seek.retotecnico.mysql.model.UserData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<UserData, String>, ReactiveQueryByExampleExecutor<UserData> {

    @Query("SELECT * FROM customer WHERE document_id = :documentId")
    Mono<UserData> findByDocumentId(@Param("documentId") String documentId);
    @Query("SELECT COUNT(*) AS total_customers, AVG(age) AS average_age, STDDEV(age) AS age_standard_deviation FROM customer")
    Mono<CustomerMetricsData> getCustomerMetrics();
    Flux<UserData> findAll();

}