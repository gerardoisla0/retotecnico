package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.util.enums.Operation;
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
public class GetTaskProcess {

    private final TaskManagerUseCase taskManagerUseCase;

    public Mono<ServerResponse> execute(Operation operation) {
        return Mono.just(operation)
                .flatMap(this::processAllTasks);
    }

    private Mono<ServerResponse> processAllTasks(Operation operation) {
        return getAllTasks()
                .flatMap(tasks -> buildSuccessResponse(tasks, operation))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(null, businessException));
    }

    protected Mono<List<Task>> getAllTasks() {
        return taskManagerUseCase.getAllTask();
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }

}
