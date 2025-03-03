package com.seek.retotecnico.mysql;

import com.seek.retotecnico.mysql.mapper.CustomerMapper;
import com.seek.retotecnico.model.customer.User;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.model.gateway.CustomerGateway;
import com.seek.retotecnico.mysql.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.seek.retotecnico.mysql.util.CustomerUtils.COMMON_STRING_DOCUMENT_ID;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_ERROR_KV_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_ERROR_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_KEY_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_KEY_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_ERROR_KV_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_ERROR_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_KEY_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_KEY_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.GET_CUSTOMER_METRICS_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_ERROR_KV_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_ERROR_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_KEY_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_KEY_RESPONSE;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_REQUEST;
import static com.seek.retotecnico.mysql.util.CustomerUtils.LIST_CUSTOMERS_RESPONSE;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerGateway {

    private final CustomerRepository customerRepository;
    @Override
    public Mono<User> findCustomerByDocumentId(String documentId) {
        return customerRepository.findByDocumentId(documentId)
                .map(CustomerMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, findCustomerByDocumentIdRQ(documentId))))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
    @Override
    public Mono<CustomerMetrics> getCustomerMetrics() {
        return customerRepository.getCustomerMetrics()
                .map(CustomerMapper.MAPPER::metricsToDomain)
                .doOnSubscribe(subscription -> log.info(GET_CUSTOMER_METRICS_REQUEST, kv(GET_CUSTOMER_METRICS_KEY_REQUEST, subscription)))
                .doOnSuccess(customerRegistries -> log.info(GET_CUSTOMER_METRICS_RESPONSE, kv(GET_CUSTOMER_METRICS_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(GET_CUSTOMER_METRICS_ERROR_RESPONSE, kv(GET_CUSTOMER_METRICS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
    @Override
    public Mono<List<User>> listAllCustomers() {
        return customerRepository.findAll()
                .map(CustomerMapper.MAPPER::dataToDomain)
                .collectList()
                .doOnSubscribe(subscription -> log.info(LIST_CUSTOMERS_REQUEST, kv(LIST_CUSTOMERS_KEY_REQUEST, subscription)))
                .doOnSuccess(customers -> log.info(LIST_CUSTOMERS_RESPONSE, kv(LIST_CUSTOMERS_KEY_RESPONSE, customers)))
                .doOnError(exception -> log.error(LIST_CUSTOMERS_ERROR_RESPONSE, kv(LIST_CUSTOMERS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
    @Override
    public Mono<User> createCustomer(User user) {
        return Mono.just(user)
                .map(CustomerMapper.MAPPER::domainToData)
                .flatMap(customerRepository::save)
                .map(CustomerMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(CREATE_CUSTOMERS_REQUEST, kv(CREATE_CUSTOMERS_KEY_REQUEST, user)))
                .doOnSuccess(customerData -> log.info(CREATE_CUSTOMERS_RESPONSE, kv(CREATE_CUSTOMERS_KEY_RESPONSE, customerData)))
                .doOnError(exception -> log.error(CREATE_CUSTOMERS_ERROR_RESPONSE, kv(CREATE_CUSTOMERS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
    private Map<String, Object> findCustomerByDocumentIdRQ(String documentId) {
        return Map.of(COMMON_STRING_DOCUMENT_ID, documentId);
    }
}
