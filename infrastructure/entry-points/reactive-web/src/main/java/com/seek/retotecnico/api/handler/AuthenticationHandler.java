package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.config.JwtTokenProvider;
import com.seek.retotecnico.api.util.UserManagerUtilApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.seek.retotecnico.api.dto.response.GenerateTokenResponseApi;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String OPERATION_PROCESS_NAME = "generateToken";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process(
            GenerateTokenRequestApi generateTokenRequestApi) {
        return execute(generateTokenRequestApi, Operation.GENERATE_TOKEN);
    }

    public Mono<ServerResponse> fallback(GenerateTokenRequestApi generateTokenRequestApi, Exception exception) {
        return UserManagerUtilApi.buildResponseFallbackAuth(generateTokenRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(GenerateTokenRequestApi generateTokenRequestApi, CallNotPermittedException callNotPermittedException) {
        return UserManagerUtilApi.buildResponseFallbackAuth(generateTokenRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

    public Mono<ServerResponse> execute(
            GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return Mono.just(generateTokenRequestApi)
                .flatMap(generateTokenRQ -> executeOperation(generateTokenRQ,operation));
    }

    private Mono<ServerResponse> executeOperation(GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return RequestValidatorHandlerApi.validateRequestAuth(generateTokenRequestApi)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> UserManagerUtilApi.buildResponseBadRequestAuth(operation, generateTokenRequestApi, errors))
                .switchIfEmpty(Mono.defer(() -> Mono.just(generateTokenRequestApi)
                        .flatMap(createUserRQ -> generateTokenAndExecuteUseCase(createUserRQ, operation))
                        .onErrorResume(BusinessException.class, businessException -> UserManagerUtilApi.buildResponseBusinessExceptionAuth(generateTokenRequestApi, businessException))
                        .doOnSubscribe(subscription -> UserManagerUtilApi.logRequest(operation, generateTokenRequestApi))));
    }

    private Mono<ServerResponse> generateTokenAndExecuteUseCase(GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return jwtTokenProvider.generateToken(generateTokenRequestApi.getUsername())
                .map(this::parsedToResponse)
                .flatMap(clientRS -> UserManagerUtilApi.buildSuccessResponseAuth(generateTokenRequestApi, clientRS, operation))
                .onErrorResume(BusinessException.class, businessException -> UserManagerUtilApi.buildResponseBusinessExceptionAuth(generateTokenRequestApi, businessException));
    }

    private GenerateTokenResponseApi parsedToResponse (String token){
        return GenerateTokenResponseApi.builder().token(token)
                .build();
    }
}
