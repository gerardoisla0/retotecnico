package com.seek.retotecnico.api;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.seek.retotecnico.api.dto.response.CreateCustomerResponseApi;
import com.seek.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.seek.retotecnico.api.handler.AuthenticationHandler;
import com.seek.retotecnico.api.handler.GetCustomerHandler;
import com.seek.retotecnico.api.handler.GetCustomerMetricsHandler;
import com.seek.retotecnico.api.handler.SaveCustomerHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.seek.retotecnico.model.util.enums.Operation;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Swagger User Manager", version = "1.0",
        description = "Documentación para la gestión de Clientes v1.0"))
public class CustomerManagerRouteRest {

    private static final String SUCCESSFUL = "Successful Operation";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(SaveCustomerHandler saveCustomerHandler, GetCustomerHandler getCustomerHandler,
                                                         GetCustomerMetricsHandler getCustomerMetricsHandler, AuthenticationHandler authenticationHandler){
        return route()
                .POST(Operation.CREATE_CUSTOMER.getPath(), request -> request
                        .bodyToMono(CreateCustomerRequestApi.class)
                        .flatMap(saveCustomerHandler::process), customerManager(Operation.CREATE_CUSTOMER))
                .GET(Operation.LIST_CUSTOMER.getPath(), request -> getCustomerHandler.process(), customerManager(Operation.LIST_CUSTOMER))
                .GET(Operation.GET_CUSTOM_METRICS.getPath(), request -> getCustomerMetricsHandler.process(), customerManager(Operation.GET_CUSTOM_METRICS))
                .POST(Operation.GENERATE_TOKEN.getPath(), request -> request
                        .bodyToMono(GenerateTokenRequestApi.class)
                        .flatMap(authenticationHandler::process), generateTokenOperation())
                .build();
    }
    private Consumer<Builder> generateTokenOperation() {
        return ops -> ops.tag(Operation.GENERATE_TOKEN.getKvRequest())
                .operationId(Operation.GENERATE_TOKEN.getName()).summary(Operation.GENERATE_TOKEN.getNameRequest())
                .tags(new String[] { "GenerateToken" })
                .requestBody(requestBodyBuilder().implementation(GenerateTokenRequestApi.class)).
                response(responseBuilder().responseCode("200")
                        .description(SUCCESSFUL)
                        .implementation(GenerateTokenResponseApi.class))
                .response(responseBuilder().responseCode("404"));
    }
    private Consumer<Builder> customerManager(Operation operation) {
        return ops -> ops.tag(operation.getKvRequest())
                .operationId(operation.getKvRequest()).summary(operation.getNameRequest())
                .tags(new String[]{"UserManager"})
                .requestBody(requestBodyBuilder().implementation(CreateCustomerRequestApi.class))
                .response(responseBuilder().responseCode("200")
                        .description(SUCCESSFUL)
                        .implementation(CreateCustomerResponseApi.class));
    }

}
