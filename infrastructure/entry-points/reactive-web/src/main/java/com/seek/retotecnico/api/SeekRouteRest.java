package com.seek.retotecnico.api;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.api.dto.response.structure.body.AuthResponseApi;
import com.seek.retotecnico.api.handler.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.seek.retotecnico.model.util.enums.Operation;

import java.util.function.Consumer;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Swagger User Manager", version = "1.0",
        description = "Documentación para la gestión de Clientes v1.0"))
public class SeekRouteRest {

    private static final String SUCCESSFUL = "Successful Operation";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(SaveCustomerHandler saveCustomerHandler,
                                                         GetTaskHandler getTaskHandler,
                                                         SaveTaskHandler saveTaskHandler,
                                                         UpdateTaskHandler updateTaskHandler,
                                                         DeleteTaskHandler deleteTaskHandler,
                                                         AuthenticationHandler authenticationHandler){
        return route()
                .POST(Operation.CREATE_USER.getPath(), request -> request
                        .bodyToMono(SeekRequestApi.class)
                        .flatMap(saveCustomerHandler::process), customerManager(Operation.CREATE_USER))
                .POST(Operation.AUTHENTICATE_USER.getPath(), request -> request
                        .bodyToMono(SeekRequestApi.class)
                        .flatMap(authenticationHandler::process), generateTokenOperation())
                .POST(Operation.CREATE_TASK.getPath(), request -> request
                        .bodyToMono(SeekRequestApi.class)
                        .flatMap(saveTaskHandler::process), customerManager(Operation.CREATE_TASK))
               .GET(Operation.GET_ALL_TASK.getPath(), request -> getTaskHandler.process(), customerManager(Operation.GET_ALL_TASK))
               .PUT(Operation.UPDATE_TASK.getPath(), updateTaskHandler::process, customerManager(Operation.UPDATE_TASK))
                .DELETE(Operation.DELETE_TASK.getPath(), deleteTaskHandler::process, customerManager(Operation.DELETE_TASK))
                .build();
    }
    private Consumer<Builder> generateTokenOperation() {
        return ops -> ops.tag(Operation.AUTHENTICATE_USER.getKvRequest())
                .operationId(Operation.AUTHENTICATE_USER.getName()).summary(Operation.AUTHENTICATE_USER.getNameRequest())
                .tags(new String[] { "GenerateToken" })
                .requestBody(requestBodyBuilder().implementation(SeekRequestApi.class)).
                response(responseBuilder().responseCode("200")
                        .description(SUCCESSFUL)
                        .implementation(AuthResponseApi.class))
                .response(responseBuilder().responseCode("404"));
    }
    private Consumer<Builder> customerManager(Operation operation) {
        return ops -> ops.tag(operation.getKvRequest())
                .operationId(operation.getKvRequest()).summary(operation.getNameRequest())
                .tags(new String[]{"UserManager"})
                .requestBody(requestBodyBuilder().implementation(SeekRequestApi.class))
                .response(responseBuilder().responseCode("200")
                        .description(SUCCESSFUL)
                        .implementation(SeekResponseApi.class));
    }

}
