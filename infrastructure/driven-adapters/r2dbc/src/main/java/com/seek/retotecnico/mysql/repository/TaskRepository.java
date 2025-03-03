package com.seek.retotecnico.mysql.repository;

import com.seek.retotecnico.mysql.model.TaskData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TaskRepository extends ReactiveCrudRepository<TaskData, Long>, ReactiveQueryByExampleExecutor<TaskData> {
}
