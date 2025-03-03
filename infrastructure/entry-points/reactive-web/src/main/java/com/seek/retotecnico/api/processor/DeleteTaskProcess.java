package com.seek.retotecnico.api.processor;

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
public class DeleteTaskProcess {

    private final TaskManagerUseCase taskManagerUseCase;

    public Mono<ServerResponse> execute(String id, Operation operation) {
        return Mono.just(operation)
                .flatMap(operationDelete -> processDelete(id, operationDelete));
    }

    private Mono<ServerResponse> processDelete(String id, Operation operation) {
        return findTask(id)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new BusinessException(TechnicalMessage.ERROR_TASK_NOT_EXIST))
                ))
                .flatMap(delTask -> deleteTask(delTask, id))
                .flatMap(tasks -> buildSuccessResponse(tasks, operation))
                .onErrorResume(BusinessException.class, this::buildBusinessErrorResponse);
    }

    private Mono<Task> findTask(String taskId) {
        return taskManagerUseCase.getTaskById(Long.parseLong(taskId));
    }

    protected Mono<Void> deleteTask(Task task, String id) {
        return taskManagerUseCase.deleteTask(task, id);
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(BusinessException exception) {
        return buildResponseBusinessException(null, exception);
    }

}
