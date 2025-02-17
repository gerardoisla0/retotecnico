package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.processor.GetCustomerMetricsProcess;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.buildResponseFallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCustomerMetricsHandler {

    private final GetCustomerMetricsProcess getCustomerMetricsProcess;
    private static final String OPERATION_PROCESS_NAME = "getCustomerMetrics";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process() {
        return getCustomerMetricsProcess.execute(Operation.GET_CUSTOM_METRICS);
    }

    public Mono<ServerResponse> fallback(Exception exception) {
        return buildResponseFallback(null, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(CallNotPermittedException callNotPermittedException) {
        return buildResponseFallback(null, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

}
