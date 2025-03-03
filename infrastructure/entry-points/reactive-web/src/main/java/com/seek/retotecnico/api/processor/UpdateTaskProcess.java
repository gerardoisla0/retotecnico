package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.mapper.TaskResponseMapperApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.taskmanager.TaskManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTaskProcess {

    private final TaskManagerUseCase taskManagerUseCase;

    public Mono<ServerResponse> execute(String id, SeekRequestApi request, Operation operation) {
        return Mono.just(request)
                .flatMap(req -> validateAndProcessTaskUpdate(id, req, operation));
    }

    private Mono<ServerResponse> validateAndProcessTaskUpdate(String id, SeekRequestApi request, Operation operation) {
        return RequestValidatorHandlerApi.validateRequestTaskUpdate(request)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequest(operation,request, errors))
                .switchIfEmpty(Mono.defer(() -> processCustomerUpdate(id, request, operation)));
    }

    private Mono<ServerResponse> processCustomerUpdate(String id, SeekRequestApi request, Operation operation) {
        return findTask(id)
                .flatMap(updTask -> updateTask(request, id))
                .flatMap(tasks -> buildSuccessResponse(tasks, operation))
                .switchIfEmpty(Mono.defer(() -> buildBusinessErrorResponse(request, new BusinessException(TechnicalMessage.ERROR_TASK_NOT_EXIST))))
                .onErrorResume(BusinessException.class, error -> this.buildBusinessErrorResponse(request, error));
    }

    private Mono<Task> findTask(String taskId) {
        return taskManagerUseCase.getTaskById(Long.parseLong(taskId));
    }

    private Mono<Task> updateTask(SeekRequestApi request, String id) {
        return Mono.just(request)
                .map(TaskResponseMapperApi.MAPPER::taskUserRequestToTaskUpdate)
                .flatMap(updTask -> taskManagerUseCase.updateTask(id, updTask));
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }
}
