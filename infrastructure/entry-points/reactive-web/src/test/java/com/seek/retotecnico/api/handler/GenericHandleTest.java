package com.seek.retotecnico.api.handler;

import com.seek.retotecnico.api.config.JwtTokenProvider;
import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.request.body.SaveUserRequest;
import com.seek.retotecnico.api.processor.GetTaskProcess;
import com.seek.retotecnico.api.processor.SaveCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class GenericHandleTest {

    @Autowired
    protected ApplicationContext context;

    @MockBean
    protected GetTaskProcess getTaskProcess;
    @MockBean
    protected GetCustomerMetricsProcess getCustomerMetricsProcess;
    @MockBean
    protected SaveCustomer saveCustomer;
    @MockBean
    protected JwtTokenProvider jwtTokenProvider;
    protected WebTestClient webTestClient;

    protected static final String FALLBACK_METHOD_NAME = "fallback";
    public static final String SERVICE_NAME = "Reto-tecnico";

    protected SaveCustomerHandler buildCreateManagerHandler() {
        return new SaveCustomerHandler(saveCustomer);
    }

    protected AuthenticationHandler buildAuthenticationHandler() {
        return new AuthenticationHandler(jwtTokenProvider);
    }

    @Test
    void shouldBuildCustomerIdentityRequestWhenBodyIsCreateUse() {

        SeekRequestApi createCustomerRequestApi = buildCreateUserRequest();

        assertNotNull(createCustomerRequestApi);
        assertNotNull(createCustomerRequestApi.getSaveCustomerRQ().getAge());
        assertNotNull(createCustomerRequestApi.getSaveCustomerRQ().getName());
        assertNotNull(createCustomerRequestApi.getSaveCustomerRQ().getLastName());
        assertNotNull(createCustomerRequestApi.getSaveCustomerRQ().getDocumentId());
        assertNotNull(createCustomerRequestApi.getSaveCustomerRQ().getBirthDay());
    }

    @Test
    void shouldBuildUnderAgeRequestWhenBodyIsGenerateToken() {

        AuthenticateUserRequestApi authenticateUserRequestApi = buildGenerateTokenRequestApi();
        assertNotNull(authenticateUserRequestApi);
        assertNotNull(authenticateUserRequestApi.getUsername());
    }

    protected SeekRequestApi buildCreateUserRequest() {
        return SeekRequestApi
                .builder()
                .saveCustomerRQ(SaveUserRequest.builder()
                        .name("Julio")
                        .lastName("Isla")
                        .age(33)
                        .birthDay("19-20-2022")
                        .documentId("1234566")
                        .build())
                .build();
    }

    protected AuthenticateUserRequestApi buildCreateGenerateToken() {
        return AuthenticateUserRequestApi
                .builder()
                .username("Julio")
                .build();
    }


    protected SeekRequestApi buildBadRequest() {
        return SeekRequestApi
                .builder()
                .saveCustomerRQ(SaveUserRequest.builder()
                        .name("Julio")
                        .lastName("Isla")
                        .age(33)
                        .birthDay("19-20-2022")
                        .build())
                .build();
    }

    protected AuthenticateUserRequestApi buildGenerateTokenRequestApi() {
        return AuthenticateUserRequestApi
                .builder().username("username")
                .build();
    }

}
