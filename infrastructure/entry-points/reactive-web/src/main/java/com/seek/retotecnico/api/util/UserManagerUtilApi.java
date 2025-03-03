package com.seek.retotecnico.api.util;

import com.seek.retotecnico.api.dto.response.SeekResponseApi;
import com.seek.retotecnico.api.dto.response.structure.body.AuthResponseApi;
import com.seek.retotecnico.api.mapper.TaskResponseMapperApi;
import com.seek.retotecnico.api.mapper.UserRequestMapperApi;
import com.seek.retotecnico.model.task.Task;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.seek.retotecnico.api.mapper.GenerateTokenMapperApi;
import com.seek.retotecnico.model.util.enums.Operation;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.seek.retotecnico.api.util.HandlerUtilApi.buildResponseService;
import static com.seek.retotecnico.api.util.HandlerUtilApi.buildResponseServiceAuth;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@UtilityClass
public class UserManagerUtilApi {

    public static Mono<ServerResponse> buildResponseBadRequest(Operation operation, SeekRequestApi createCustomerRequestApi, List<ErrorDetail> errors) {
        return buildCreateUserResponse(createCustomerRequestApi,  operation.getName(), TechnicalMessage.ERROR_BAD_REQUEST, errors, null, null);
    }

    public static Mono<ServerResponse> buildCreateUserResponse(SeekRequestApi createCustomerRequestApi,
                                                               String nameOperation, TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
                                                               BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            SeekResponseApi seekResponseApi = TaskResponseMapperApi.MAPPER.requestToResponse(
                    createCustomerRequestApi, technicalMessage);
            return buildResponseService(seekResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildResponseFallback(SeekRequestApi createCustomerRequestApi, TechnicalMessage technicalMessage, Exception exception) {
        return buildCreateUserResponse(createCustomerRequestApi,  null, technicalMessage, null, null, exception);
    }

    public static Mono<ServerResponse> buildSuccessResponse(Object response, Operation operation) {
        return Mono.defer(() -> {
            if (response instanceof User client) {
                SeekResponseApi seekResponseApi = UserRequestMapperApi.MAPPER.clientToCreateUserResponse(client, TechnicalMessage.SUCCESS);
                return buildResponseService(seekResponseApi, operation.getName(), null, null, null);
            }
            else if (response instanceof Task task) {
                SeekResponseApi seekResponseApi = TaskResponseMapperApi.MAPPER.taskToCreateTaskResponse(task, TechnicalMessage.SUCCESS);
                return buildResponseService(seekResponseApi, operation.getName(), null, null, null);
            }
           else if (response instanceof List<?> tasks && !tasks.isEmpty() && tasks.get(0) instanceof Task) {
                List<Task> taskList = (List<Task>) tasks;
                SeekResponseApi seekResponseApi = TaskResponseMapperApi.MAPPER.listToTaskResponse(taskList, TechnicalMessage.SUCCESS);
                return buildResponseService(seekResponseApi, operation.getName(), null, null, null);
            }else{
                return null;
            }
        });
    }

    public static Mono<ServerResponse> buildSuccessResponseAuth(AuthResponseApi authRS, Operation operation) {
        return Mono.defer(() -> {

            SeekResponseApi seekResponseApi  = GenerateTokenMapperApi.MAPPER.requestToGetGenerateTokenResponse(authRS, TechnicalMessage.SUCCESS);
            return buildResponseServiceAuth(seekResponseApi, operation.getName(), null, null, null);
        });
    }

    public static Mono<ServerResponse> buildResponseBusinessException(SeekRequestApi createCustomerRequestApi, BusinessException businessException) {
        return buildUserManagerResponse(createCustomerRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildResponseBusinessExceptionAuth(SeekRequestApi authenticateUserRequestApi, BusinessException businessException) {
        return buildUserManagerResponseAuth(authenticateUserRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildUserManagerResponse(
            SeekRequestApi createCustomerRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            SeekResponseApi seekResponseApi = TaskResponseMapperApi.MAPPER.requestToResponse(
                    createCustomerRequestApi, technicalMessage);
            return buildResponseService(seekResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildUserManagerResponseAuth(SeekRequestApi authenticateUserRequestApi,
                                                                    String nameOperation, TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
                                                                    BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            SeekResponseApi seekResponseApi = GenerateTokenMapperApi.MAPPER.requestToResponse(technicalMessage);
            return buildResponseServiceAuth(seekResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static void logRequest(Operation operation, Object userLockRequest) {
        log.info(operation.getNameRequest(), kv(operation.getKvRequest(), userLockRequest));
    }
}
