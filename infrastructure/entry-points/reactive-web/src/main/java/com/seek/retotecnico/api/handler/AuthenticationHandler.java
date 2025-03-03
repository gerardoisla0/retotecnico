package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.config.JwtTokenProvider;
import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.api.dto.response.structure.body.AuthResponseApi;
import com.seek.retotecnico.api.util.UserManagerUtilApi;
import com.seek.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.buildResponseBusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserManagerUseCase userManagerUseCase;

    private static final String OPERATION_PROCESS_NAME = "authenticateUser";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process(
            SeekRequestApi seekRequestApi) {
        return execute(seekRequestApi, Operation.AUTHENTICATE_USER);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi seekRequestApi, Exception exception) {
        return UserManagerUtilApi.buildResponseFallback(seekRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(SeekRequestApi seekRequestApi, CallNotPermittedException callNotPermittedException) {
        return UserManagerUtilApi.buildResponseFallback(seekRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

    public Mono<ServerResponse> execute(
            SeekRequestApi seekRequestApi, Operation operation) {
        return Mono.just(seekRequestApi)
                .flatMap(generateTokenRQ -> executeOperation(generateTokenRQ,operation));
    }

    private Mono<ServerResponse> executeOperation(SeekRequestApi seekRequestApi, Operation operation) {
        return RequestValidatorHandlerApi.validateRequestAuth(seekRequestApi)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> UserManagerUtilApi.buildResponseBadRequest(operation, seekRequestApi, errors))
                .switchIfEmpty(Mono.defer(() -> Mono.just(seekRequestApi)
                        .flatMap(createUserRQ -> generateTokenAndExecuteUseCase(createUserRQ, operation))
                        .onErrorResume(BusinessException.class, businessException -> UserManagerUtilApi.buildResponseBusinessExceptionAuth(seekRequestApi, businessException))
                        .doOnSubscribe(subscription -> UserManagerUtilApi.logRequest(operation, seekRequestApi))));
    }

    private Mono<ServerResponse> generateTokenAndExecuteUseCase(SeekRequestApi seekRequestApi, Operation operation) {
        return userManagerUseCase.loginUser(seekRequestApi.getAuthUserRQ().getEmail(), seekRequestApi.getAuthUserRQ().getPassword())
                .flatMap(userLogin -> jwtTokenProvider.generateToken(seekRequestApi.getAuthUserRQ().getEmail())
                        .map(this::parsedToResponse)
                        .flatMap(clientRS -> UserManagerUtilApi.buildSuccessResponseAuth(clientRS, operation))
                )
                .onErrorResume(BusinessException.class, businessException
                        -> UserManagerUtilApi.buildResponseBusinessExceptionAuth(seekRequestApi, businessException))
                .switchIfEmpty(Mono.defer(() -> {
                    return buildBusinessErrorResponse(seekRequestApi, new BusinessException(TechnicalMessage.ERROR_INCORRECT_CREDENTIALS));
                }));
    }

    private AuthResponseApi parsedToResponse (String token){
        return AuthResponseApi.builder().token(token)
                .build();
    }

    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }
}
