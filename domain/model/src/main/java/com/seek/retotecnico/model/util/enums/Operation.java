package com.seek.retotecnico.model.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Operation {

    CUSTOMER("customer", null, "Customer Manager Request", "userManagerRQ", "Customer Manager Response", "customerManagerRS"),
    CREATE_CUSTOMER("createCustomer", "/api/v1/customers/create", "Create Customer Request", "createCustomerRQ", "Customer Manager Response", "createCustomerRS"),
    GET_CUSTOM_METRICS("getCustomMetrics", "/api/v1/customers/metrics", "Customer Metrics Request", "customerMetricsRQ", "Create Customer Response", "customerMetricsRS"),
    LIST_CUSTOMER("listCustomers", "/api/v1/customers/list", "Customers List Request", "customerListRQ", "Customer List Response", "customerListRS"),
    GENERATE_TOKEN("generateToken", "/api/v1/customers/token", "Generate Token Request", "generateTokenRQ", "Generate Token Response", "generateTokenRS");

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
