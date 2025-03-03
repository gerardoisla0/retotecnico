package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.processor.SaveCustomer;
import com.seek.retotecnico.model.util.enums.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveCustomerHandler {

    private final SaveCustomer saveCustomer;
    private static final String OPERATION_PROCESS_NAME = "createUser";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process(SeekRequestApi seekRequestApi) {
        return saveCustomer.execute(seekRequestApi, Operation.CREATE_USER);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi createCustomerRequestApi, Exception exception) {
        return buildResponseFallback(createCustomerRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi createCustomerRequestApi, CallNotPermittedException callNotPermittedException) {
        return buildResponseFallback(createCustomerRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

}

