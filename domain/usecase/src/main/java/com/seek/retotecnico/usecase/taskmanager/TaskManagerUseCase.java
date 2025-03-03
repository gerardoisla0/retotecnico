package com.seek.retotecnico.usecase.taskmanager;

import com.seek.retotecnico.model.gateway.TaskGateway;
import com.seek.retotecnico.model.task.Task;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TaskManagerUseCase {
    private final TaskGateway taskGateway;

    public Mono<List<Task>> getAllTask() {
        return taskGateway.getAllTask();
    };
    public Mono<Task> getTaskById(Long id){
        return taskGateway.getTaskById(id);
    };
    public Mono<Task> createTask(Task task){
        return taskGateway.createTask(task);
    };
    public Mono<Task> updateTask(String id, Task task){
        return taskGateway.updateTask(id,task);
    };
    public Mono<Void> deleteTask(Task task, String id){
        return taskGateway.delete(task, id);
    };
}
