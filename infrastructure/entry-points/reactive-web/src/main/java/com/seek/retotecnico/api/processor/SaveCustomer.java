package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.mapper.UserRequestMapperApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
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
    private final UserManagerUseCase userManagerUseCase;

    public Mono<ServerResponse> execute(SeekRequestApi request, Operation operation) {
        return Mono.just(request)
                .flatMap(req -> validateAndProcessCustomerCreation(req, operation));
    }

    private Mono<ServerResponse> validateAndProcessCustomerCreation(SeekRequestApi request, Operation operation) {
        return RequestValidatorHandlerApi.validateRequest(request)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequest(operation,request, errors))
                .switchIfEmpty(Mono.defer(() -> processCustomerCreation(request, operation)));
    }

    private Mono<ServerResponse> processCustomerCreation(SeekRequestApi request, Operation operation) {
        return findCustomer(request)
                .flatMap(existingCustomer -> buildBusinessErrorResponse(request, new BusinessException(TechnicalMessage.ERROR_CLIENT_EXIST)))
                .switchIfEmpty(Mono.defer(() -> createCustomer(request)
                        .flatMap(customer -> buildSuccessResponse(customer, operation))))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(request, businessException));
    }

    private Mono<User> findCustomer(SeekRequestApi request) {
        return findOrCreateCustomer(request, userManagerUseCase::findUser);
    }

    private Mono<User> createCustomer(SeekRequestApi request) {
        return findOrCreateCustomer(request, userManagerUseCase::saveUser);
    }

    private Mono<User> findOrCreateCustomer(SeekRequestApi request, Function<User, Mono<User>> action) {
        return Mono.just(request)
                .map(UserRequestMapperApi.MAPPER::saveUserRequestToClient)
                .flatMap(action);
    }
    private String buildMessage(User user) {
        return "{\"name\": " + user.getName() + ", \"documentId\": \"" + user.getEmail() + "\"}";
    }
    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }
}
