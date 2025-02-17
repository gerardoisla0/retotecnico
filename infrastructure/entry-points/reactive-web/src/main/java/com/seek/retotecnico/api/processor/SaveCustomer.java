package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.mapper.ClientRequestMapperApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.usermanager.CustomerManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;
import static com.seek.retotecnico.api.util.UserManagerUtilApi.buildResponseBusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveCustomer {
    private final CustomerManagerUseCase customerManagerUseCase;

    public Mono<ServerResponse> execute(CreateCustomerRequestApi request, Operation operation) {
        return Mono.just(request)
                .flatMap(req -> validateAndProcessCustomerCreation(req, operation));
    }

    private Mono<ServerResponse> validateAndProcessCustomerCreation(CreateCustomerRequestApi request, Operation operation) {
        return RequestValidatorHandlerApi.validateRequest(request)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequest(operation,request, errors))
                .switchIfEmpty(Mono.defer(() -> processCustomerCreation(request, operation)));
    }

    private Mono<ServerResponse> processCustomerCreation(CreateCustomerRequestApi request, Operation operation) {
        return findCustomer(request)
                .flatMap(existingCustomer -> buildBusinessErrorResponse(request, new BusinessException(TechnicalMessage.ERROR_CLIENT_EXIST)))
                .switchIfEmpty(Mono.defer(() -> createCustomer(request)
                        .flatMap(customer -> buildSuccessResponse(customer, operation))))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(request, businessException));
    }

    private Mono<Customer> findCustomer(CreateCustomerRequestApi request) {
        return findOrCreateCustomer(request, customerManagerUseCase::findCustomerByDocumentId);
    }

    private Mono<Customer> createCustomer(CreateCustomerRequestApi request) {
        return findOrCreateCustomer(request, customerManagerUseCase::saveCustomer);
               // .flatMap(this::sendMessage);
    }

   /* Mono<Customer> sendMessage(Customer customer) {
        return sqsUseCase.sendMessage(buildMessage(customer))
                .thenReturn(customer);
    }*/

    private Mono<Customer> findOrCreateCustomer(CreateCustomerRequestApi request, Function<Customer, Mono<Customer>> action) {
        return Mono.just(request)
                .map(ClientRequestMapperApi.MAPPER::createCustomerRequestToClient)
                .flatMap(action);
    }
    private String buildMessage(Customer customer) {
        return "{\"name\": " + customer.getName() + ", \"documentId\": \"" + customer.getDocumentId() + "\"}";
    }
    private Mono<ServerResponse> buildBusinessErrorResponse(CreateCustomerRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }
}
