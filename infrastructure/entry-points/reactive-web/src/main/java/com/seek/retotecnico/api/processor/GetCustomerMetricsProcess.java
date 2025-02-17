package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.usermanager.CustomerManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.buildResponseBusinessException;
import static com.seek.retotecnico.api.util.UserManagerUtilApi.buildSuccessResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetCustomerMetricsProcess {
    private final CustomerManagerUseCase customerManagerUseCase;
    public Mono<ServerResponse> execute(Operation operation) {
        return Mono.just(operation)
                .flatMap(this::processCustomerMetrics);
    }

    private Mono<ServerResponse> processCustomerMetrics(Operation operation) {
        return getMetrics()
                .flatMap(metrics -> buildSuccessResponse(metrics, operation))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(null, businessException));
    }

    protected Mono<CustomerMetrics> getMetrics() {
        return customerManagerUseCase.getCustomerMetrics();
    }
    private Mono<ServerResponse> buildBusinessErrorResponse(CreateCustomerRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }

}
