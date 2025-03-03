package com.seek.retotecnico.mysql;

import com.seek.retotecnico.mysql.mapper.UserMapper;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.model.gateway.UserGateway;
import com.seek.retotecnico.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.seek.retotecnico.mysql.util.CustomerUtils.*;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserGateway {
    private final UserRepository userRepository;
    @Override
    public Mono<User> findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password)
                .map(UserMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, findUserByEmailAndPasswordRQ(email, password))))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

    @Override
    public Mono<User> findUser(User user) {
        return userRepository.findUserByEmail(user.getEmail())
                .map(UserMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, subscription)))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

    @Override
    public Mono<User> createUser(User user) {
        return Mono.just(user)
                .map(UserMapper.MAPPER::domainToData)
                .flatMap(userRepository::save)
                .map(UserMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(CREATE_CUSTOMERS_REQUEST, kv(CREATE_CUSTOMERS_KEY_REQUEST, user)))
                .doOnSuccess(customerData -> log.info(CREATE_CUSTOMERS_RESPONSE, kv(CREATE_CUSTOMERS_KEY_RESPONSE, customerData)))
                .doOnError(exception -> log.error(CREATE_CUSTOMERS_ERROR_RESPONSE, kv(CREATE_CUSTOMERS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
    private Map<String, Object> findUserByEmailAndPasswordRQ(String email, String password) {
        return Map.of(COMMON_STRING_EMAIL, email,
                        COMMON_STRING_PASSWORD, password);
    }
}
