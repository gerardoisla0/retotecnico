package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.mapper.TaskResponseMapperApi;
import com.seek.retotecnico.api.mapper.UserRequestMapperApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.taskmanager.TaskManagerUseCase;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveTaskProcess {

    private final TaskManagerUseCase taskManagerUseCase;

    public Mono<ServerResponse> execute(SeekRequestApi request, Operation operation) {
        return Mono.just(request)
                .flatMap(req -> validateAndProcessTaskCreation(req, operation));
    }

    private Mono<ServerResponse> validateAndProcessTaskCreation(SeekRequestApi request, Operation operation) {
        return RequestValidatorHandlerApi.validateRequestTask(request)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequest(operation,request, errors))
                .switchIfEmpty(Mono.defer(() -> processCustomerCreation(request, operation)));
    }

    private Mono<ServerResponse> processCustomerCreation(SeekRequestApi request, Operation operation) {
        return createTask(request)
                .flatMap(customer -> buildSuccessResponse(customer, operation))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(request, businessException));
    }

    private Mono<Task> createTask(SeekRequestApi request) {
        return Mono.just(request)
                .map(TaskResponseMapperApi.MAPPER::taskUserRequestToClient)
                .flatMap(taskManagerUseCase::createTask);
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }
}
