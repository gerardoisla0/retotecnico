package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.CustomerManagerRouteRest;
import com.seek.retotecnico.api.config.JwtSecurityConfig;
import com.seek.retotecnico.api.config.JwtTokenProvider;
import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.seek.retotecnico.api.dto.response.CreateCustomerResponseApi;
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
@ContextConfiguration(classes = { CustomerManagerRouteRest.class, JwtSecurityConfig.class,
        SaveCustomerHandler.class, AuthenticationHandler.class, JwtTokenProvider.class, GetCustomerMetricsHandler.class, GetCustomerHandler.class})
class saveCustomerHandlerTest extends GenericHandleTest {
    private static final Operation OPERATION = Operation.CREATE_CUSTOMER;
    private static final Operation OPERATION_GT = Operation.GENERATE_TOKEN;
    private CreateCustomerRequestApi createCustomerRequestApi;
    private GenerateTokenRequestApi generateTokenRequestApi;

    @BeforeEach
    public void setUp() {

        createCustomerRequestApi = buildCreateUserRequest();
        generateTokenRequestApi = buildCreateGenerateToken();

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
                        CreateCustomerRequestApi.class).exchange()
                .expectStatus().is5xxServerError()
                .expectBody(CreateCustomerResponseApi.class);


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
                        CreateCustomerRequestApi.class).exchange()
                .expectStatus().is5xxServerError()
                .expectBody(CreateCustomerResponseApi.class);

    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenFallbackCircuitBreaker() throws Throwable {

        SaveCustomerHandler target = buildCreateManagerHandler();
        Method testMethod = target.getClass().getMethod("process",
                CreateCustomerRequestApi.class);

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
                GenerateTokenRequestApi.class);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(OPERATION_GT.getName());

        FallbackMethod fallbackMethod = FallbackMethod.create(
                FALLBACK_METHOD_NAME, testMethod,
                new Object[] { generateTokenRequestApi }, target);

        Mono<ServerResponse> responseError = (Mono<ServerResponse>) fallbackMethod.fallback(
                CallNotPermittedException.createCallNotPermittedException(
                        circuitBreaker));

        StepVerifier.create(responseError).assertNext(response -> assertThat(response.statusCode()).isEqualTo(
                HttpStatus.OK)).verifyComplete();
    }
}
