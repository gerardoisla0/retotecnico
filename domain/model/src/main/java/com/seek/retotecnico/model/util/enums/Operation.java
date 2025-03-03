package com.seek.retotecnico.model.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Operation {

    CUSTOMER("user", null, "User Manager Request", "userManagerRQ", "User Manager Response", "userManagerRS"),
    CREATE_USER("createUser", "/api/v1/user/create", "Create User Request", "createUserRQ", "Create User Response", "createUserRS"),
    AUTHENTICATE_USER("authenticateUser", "/api/v1/user/login", "Authenticate Request", "authUserRQ", "Authenticate Response", "authUserRS"),
    CREATE_TASK("createTask", "/api/v1/task/create", "Create Task Request", "createTaskRQ", "Create Task Response", "createTaskRS"),
    GET_TASK("getTask", "/api/v1/task/get", "Get Task Request", "getTaskRQ", "Get Task Response", "getTaskRS"),
    UPDATE_TASK("updateTask", "/api/v1/task/update/{id}", "Update Task Request", "updateTaskRQ", "Update Task Response", "updateTaskRS"),
    DELETE_TASK("deleteTask", "/api/v1/task/delete/{id}", "Delete Task Request", "deleteTaskRQ", "Delete Task Response", "deleteTaskRS"),
    GET_ALL_TASK("getAllTask", "/api/v1/task/all", "Get All Task Request", "getAllTaskRQ", "Get All Task Response", "getAllTaskRS");

    private final String name;
    private final String path;
    private final String nameRequest;
    private final String kvRequest;
    private final String nameResponse;
    private final String kvResponse;

    private static final Map<String, Operation> BY_OPERATION = new HashMap<>();

    static {
        for (Operation operation : values()) {
            BY_OPERATION.put(operation.name, operation);
        }
    }

    public static Operation findByName(String name) {
        return BY_OPERATION.computeIfAbsent(name, key -> {
            throw new IllegalArgumentException(String.format("La operación %s no se encuentra registrada", key));
        });
    }
}
