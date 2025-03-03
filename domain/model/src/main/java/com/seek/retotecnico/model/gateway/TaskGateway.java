package com.seek.retotecnico.model.gateway;

import com.seek.retotecnico.model.task.Task;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TaskGateway {
    Mono<List<Task>> getAllTask();
    Mono<Task> getTaskById(Long id);
    Mono<Task> createTask(Task task);
    Mono<Task> updateTask(String id, Task task);
    Mono<Void> delete(Task task, String id);
}
