package com.seek.retotecnico.mysql;

import com.seek.retotecnico.model.gateway.TaskGateway;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.mysql.mapper.TaskMapper;
import com.seek.retotecnico.mysql.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.seek.retotecnico.mysql.util.CustomerUtils.*;
import static com.seek.retotecnico.mysql.util.CustomerUtils.CREATE_CUSTOMERS_ERROR_KV_RESPONSE;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TaskAdapter implements TaskGateway {
    private final TaskRepository taskRepository;
    @Override
    public Mono<List<Task>> getAllTask() {
        return taskRepository.findAll()
                .map(TaskMapper.MAPPER::dataToDomain)
                .collectList()
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, subscription)))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

    @Override
    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, subscription)))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));

    }

    @Override
    public Mono<Task> createTask(Task task) {
        return Mono.just(task)
                .map(TaskMapper.MAPPER::domainToDataWitouthID)
                .flatMap(taskRepository::save)
                .map(TaskMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(CREATE_CUSTOMERS_REQUEST, kv(CREATE_CUSTOMERS_KEY_REQUEST, task)))
                .doOnSuccess(customerData -> log.info(CREATE_CUSTOMERS_RESPONSE, kv(CREATE_CUSTOMERS_KEY_RESPONSE, customerData)))
                .doOnError(exception -> log.error(CREATE_CUSTOMERS_ERROR_RESPONSE, kv(CREATE_CUSTOMERS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

    @Override
    public Mono<Task> updateTask(String id, Task task) {
        return Mono.just(task)
                .map(taskUpdate -> TaskMapper.MAPPER.domainToData(taskUpdate, Long.parseLong(id)))
                .flatMap(taskRepository::save)
                .map(TaskMapper.MAPPER::dataToDomain)
                .doOnSubscribe(subscription -> log.info(CREATE_CUSTOMERS_REQUEST, kv(CREATE_CUSTOMERS_KEY_REQUEST, task)))
                .doOnSuccess(customerData -> log.info(CREATE_CUSTOMERS_RESPONSE, kv(CREATE_CUSTOMERS_KEY_RESPONSE, customerData)))
                .doOnError(exception -> log.error(CREATE_CUSTOMERS_ERROR_RESPONSE, kv(CREATE_CUSTOMERS_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

    @Override
    public Mono<Void> delete(Task task, String id) {
        return Mono.just(task)
                .map(taskDelete -> TaskMapper.MAPPER.domainToData(taskDelete, Long.parseLong(id)))
                .flatMap(taskRepository::delete)
                .doOnSubscribe(subscription -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_REQUEST, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_REQUEST, subscription)))
                .doOnSuccess(customerRegistries -> log.info(FIND_CUSTOMER_BY_DOCUMENT_ID_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_KEY_RESPONSE, customerRegistries)))
                .doOnError(exception -> log.error(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_RESPONSE, kv(FIND_CUSTOMER_BY_DOCUMENT_ID_ERROR_KV_RESPONSE, exception)))
                .onErrorMap(DataIntegrityViolationException.class, exception -> new BusinessException(TechnicalMessage.ERROR_INTERNAL_SERVER));
    }
}
