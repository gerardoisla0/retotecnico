package com.seek.retotecnico.api.util;

import com.seek.retotecnico.api.dto.response.CreateCustomerResponseApi;
import com.seek.retotecnico.api.mapper.ClientResponseMapperApi;
import com.seek.retotecnico.model.customer.Customer;
import com.seek.retotecnico.model.metrics.CustomerMetrics;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.seek.retotecnico.api.dto.response.GenerateTokenResponseApi;
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

    public static Mono<ServerResponse> buildResponseBadRequest(Operation operation, CreateCustomerRequestApi createCustomerRequestApi, List<ErrorDetail> errors) {
        return buildCreateUserResponse(createCustomerRequestApi,  operation.getName(), TechnicalMessage.ERROR_BAD_REQUEST, errors, null, null);
    }

    public static Mono<ServerResponse> buildResponseBadRequestAuth(Operation operation, GenerateTokenRequestApi generateTokenRequestApi, List<ErrorDetail> errors) {
        return buildCreateUserResponseAuth(generateTokenRequestApi,  operation.getName(), TechnicalMessage.ERROR_BAD_REQUEST, errors, null, null);
    }

    public static Mono<ServerResponse> buildCreateUserResponse(CreateCustomerRequestApi createCustomerRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.requestToResponse(
                    createCustomerRequestApi, technicalMessage);
            return buildResponseService(createCustomerResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildCreateUserResponseAuth(CreateCustomerRequestApi createCustomerRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.requestToResponse(
                    createCustomerRequestApi, technicalMessage);
            return buildResponseService(createCustomerResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildCreateUserResponseAuth(GenerateTokenRequestApi generateTokenRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            GenerateTokenResponseApi generateTokenResponseApi = GenerateTokenMapperApi.MAPPER.requestToResponse(generateTokenRequestApi, technicalMessage);
            return buildResponseServiceAuth(generateTokenResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildResponseFallback(CreateCustomerRequestApi createCustomerRequestApi, TechnicalMessage technicalMessage, Exception exception) {
        return buildCreateUserResponse(createCustomerRequestApi,  null, technicalMessage, null, null, exception);
    }

    public static Mono<ServerResponse> buildResponseFallbackAuth(GenerateTokenRequestApi generateTokenRequestApi, TechnicalMessage technicalMessage, Exception exception) {
        return buildCreateUserResponseAuth(generateTokenRequestApi,  null, technicalMessage, null, null, exception);
    }
    public static Mono<ServerResponse> buildSuccessResponse(Object response, Operation operation) {
        return Mono.defer(() -> {
            if (response instanceof Customer client) {
                CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.clientToCreateUserResponse(client, TechnicalMessage.SUCCESS);
                return buildResponseService(createCustomerResponseApi, operation.getName(), null, null, null);
            }
            else if (response instanceof CustomerMetrics metrics) {
                CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.metricsToMetricsResponse(metrics, TechnicalMessage.SUCCESS);
                return buildResponseService(createCustomerResponseApi, operation.getName(), null, null, null);

            }else if (response instanceof List<?> customers && !customers.isEmpty() && customers.get(0) instanceof Customer) {
                List<Customer> customerList = (List<Customer>) customers;
                CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.listToCustomerResponse(customerList, TechnicalMessage.SUCCESS);
                return buildResponseService(createCustomerResponseApi, operation.getName(), null, null, null);
            }else{
                return null;
            }
        });
    }

    public static Mono<ServerResponse> buildSuccessResponseAuth(GenerateTokenRequestApi generateTokenRequestApi, GenerateTokenResponseApi getUserLocksRS, Operation operation) {
        return Mono.defer(() -> {

            GenerateTokenResponseApi generateTokenResponseApi = GenerateTokenMapperApi.MAPPER.requestToGetGenerateTokenResponse(generateTokenRequestApi, getUserLocksRS, TechnicalMessage.SUCCESS);
            return buildResponseServiceAuth(generateTokenResponseApi, operation.getName(), null, null, null);
        });
    }

    public static Mono<ServerResponse> buildResponseBusinessException(CreateCustomerRequestApi createCustomerRequestApi, BusinessException businessException) {
        return buildUserManagerResponse(createCustomerRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildResponseBusinessExceptionAuth(GenerateTokenRequestApi generateTokenRequestApi, BusinessException businessException) {
        return buildUserManagerResponseAuth(generateTokenRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildUserManagerResponse(
            CreateCustomerRequestApi createCustomerRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateCustomerResponseApi createCustomerResponseApi = ClientResponseMapperApi.MAPPER.requestToResponse(
                    createCustomerRequestApi, technicalMessage);
            return buildResponseService(createCustomerResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildUserManagerResponseAuth(GenerateTokenRequestApi generateTokenRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            GenerateTokenResponseApi generateTokenResponseApi  = GenerateTokenMapperApi.MAPPER.requestToResponse(generateTokenRequestApi, technicalMessage);
            return buildResponseServiceAuth(generateTokenResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static void logRequest(Operation operation, Object userLockRequest) {
        log.info(operation.getNameRequest(), kv(operation.getKvRequest(), userLockRequest));
    }
}
