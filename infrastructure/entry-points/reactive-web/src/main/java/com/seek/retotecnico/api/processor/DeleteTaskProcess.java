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

import java.util.List;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTaskProcess {

    private final String id;
    private final TaskManagerUseCase taskManagerUseCase;

    public Mono<ServerResponse> execute(Operation operation) {
        return Mono.just(operation)
                .flatMap(this::processDelete);
    }

    private Mono<ServerResponse> processDelete(Operation operation) {
        return findTask(id)
                .flatMap(this::deleteTask)
                .flatMap(tasks -> buildSuccessResponse(tasks, operation))
                .switchIfEmpty(Mono.defer(() -> buildBusinessErrorResponse(new BusinessException(TechnicalMessage.ERROR_TASK_NOT_EXIST))))
                .onErrorResume(BusinessException.class, this::buildBusinessErrorResponse);
    }

    private Mono<Task> findTask(String taskId) {
        return taskManagerUseCase.getTaskById(Long.parseLong(taskId));
    }

    protected Mono<Void> deleteTask(Task task) {
        return taskManagerUseCase.deleteTask(task,id);
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(BusinessException exception) {
        return buildResponseBusinessException(null, exception);
    }

}
