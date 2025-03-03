package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.SeekRouteRest;
import com.seek.retotecnico.api.config.JwtSecurityConfig;
import com.seek.retotecnico.api.config.JwtTokenProvider;
import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.fallback.FallbackMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = { SeekRouteRest.class, JwtSecurityConfig.class,
        SaveCustomerHandler.class, AuthenticationHandler.class, JwtTokenProvider.class, GetCustomerMetricsHandler.class, GetTaskHandler.class})
class saveUserHandlerTest extends GenericHandleTest {
    private static final Operation OPERATION = Operation.CREATE_CUSTOMER;
    private static final Operation OPERATION_GT = Operation.GENERATE_TOKEN;
    private SeekRequestApi createCustomerRequestApi;
    private AuthenticateUserRequestApi authenticateUserRequestApi;

    @BeforeEach
    public void setUp() {

        createCustomerRequestApi = buildCreateUserRequest();
        authenticateUserRequestApi = buildCreateGenerateToken();

        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient().build();

    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenClientExists() {

        given(saveCustomer.execute(any(), any())).willReturn(Mono.error(
                new BusinessException(TechnicalMessage.ERROR_CLIENT_EXIST)));

        webTestClient.post().uri(OPERATION.getPath())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createCustomerRequestApi),
                        SeekRequestApi.class).exchange()
                .expectStatus().is5xxServerError()
                .expectBody(SeekResponseApi.class);


        verify(saveCustomer, times(1)).execute(any(),any());
    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenBadRequest() {

        createCustomerRequestApi = buildBadRequest();

        given(saveCustomer.execute(any(),any())).willReturn(Mono.error(
                new BusinessException(TechnicalMessage.ERROR_BAD_REQUEST)));

        webTestClient.post().uri(OPERATION.getPath())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createCustomerRequestApi),
                        SeekRequestApi.class).exchange()
                .expectStatus().is5xxServerError()
                .expectBody(SeekResponseApi.class);

    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenFallbackCircuitBreaker() throws Throwable {

        SaveCustomerHandler target = buildCreateManagerHandler();
        Method testMethod = target.getClass().getMethod("process",
                SeekRequestApi.class);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(OPERATION.getName());

        FallbackMethod fallbackMethod = FallbackMethod.create(
                FALLBACK_METHOD_NAME, testMethod,
                new Object[] { createCustomerRequestApi }, target);

        Mono<ServerResponse> responseError = (Mono<ServerResponse>) fallbackMethod.fallback(
                CallNotPermittedException.createCallNotPermittedException(
                        circuitBreaker));

        StepVerifier.create(responseError).assertNext(response -> assertThat(response.statusCode()).isEqualTo(
                HttpStatus.INTERNAL_SERVER_ERROR)).verifyComplete();
    }

    @Test
    @WithMockUser
    void shouldPostGenerateTokenWhenFallbackCircuitBreaker() throws Throwable {

        AuthenticationHandler target = buildAuthenticationHandler();
        Method testMethod = target.getClass().getMethod("process",
                AuthenticateUserRequestApi.class);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(OPERATION_GT.getName());

        FallbackMethod fallbackMethod = FallbackMethod.create(
                FALLBACK_METHOD_NAME, testMethod,
                new Object[] {authenticateUserRequestApi}, target);

        Mono<ServerResponse> responseError = (Mono<ServerResponse>) fallbackMethod.fallback(
                CallNotPermittedException.createCallNotPermittedException(
                        circuitBreaker));

        StepVerifier.create(responseError).assertNext(response -> assertThat(response.statusCode()).isEqualTo(
                HttpStatus.OK)).verifyComplete();
    }
}
