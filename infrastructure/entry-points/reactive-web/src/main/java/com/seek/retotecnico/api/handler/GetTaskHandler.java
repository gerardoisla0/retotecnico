package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.processor.GetCustomerProcess;
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
public class GetCustomerHandler {

    private final GetCustomerProcess getCustomerProcess;
    private static final String OPERATION_PROCESS_NAME = "getCustomers";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process() {
        return getCustomerProcess.execute(Operation.CUSTOMER);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi createCustomerRequestApi, Exception exception) {
        return buildResponseFallback(createCustomerRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi createCustomerRequestApi, CallNotPermittedException callNotPermittedException) {
        return buildResponseFallback(createCustomerRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

}
