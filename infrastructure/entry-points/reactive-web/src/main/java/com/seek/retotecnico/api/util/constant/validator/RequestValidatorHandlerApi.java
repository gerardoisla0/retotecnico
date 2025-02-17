package com.seek.retotecnico.api.util.constant.validator;

import com.seek.retotecnico.api.dto.request.CreateCustomerRequestApi;
import com.seek.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.seek.retotecnico.api.util.validator.ValidatorUtilApi;
import com.seek.retotecnico.helper.validator.ValidatorUtil;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.api.dto.request.GenerateTokenRequestApi;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.*;

@UtilityClass
public class RequestValidatorHandlerApi {

    public static Mono<List<ErrorDetail>> validateRequest(CreateCustomerRequestApi createCustomerRequestApi) {
        return validateRequestStructure(createCustomerRequestApi)
                .filter(isValidRequest -> isValidRequest)
                .flatMap(isValidRequest -> validateStructureBody(createCustomerRequestApi))
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    public static Mono<List<ErrorDetail>> validateRequestAuth(GenerateTokenRequestApi generateTokenRequestApi) {
        return Mono.just(generateTokenRequestApi)
                .flatMap(RequestValidatorHandlerApi::validateStructureBodyAuth)
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    private static Mono<Boolean> validateRequestStructure(CreateCustomerRequestApi createCustomerRequestApi) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(
                createCustomerRequestApi)
                && Objects.nonNull(createCustomerRequestApi.getSaveCustomerRQ().getName())
                && Objects.nonNull(createCustomerRequestApi.getSaveCustomerRQ().getLastName())
                && Objects.nonNull(createCustomerRequestApi.getSaveCustomerRQ().getDocumentId())
                && Objects.nonNull(createCustomerRequestApi.getSaveCustomerRQ().getBirthDay())
                && Objects.nonNull(createCustomerRequestApi.getSaveCustomerRQ().getAge())));
    }

    private static Mono<List<ErrorDetail>> validateStructureBody( CreateCustomerRequestApi createCustomerRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveCustomerRQ().getName()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveCustomerRQ().getLastName()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveCustomerRQ().getDocumentId()),
                        ValidatorUtil.isValidNotNull(createCustomerRequestApi.getSaveCustomerRQ().getAge()),
                        ValidatorUtil.isValidNotNull(createCustomerRequestApi.getSaveCustomerRQ().getBirthDay())
                ).map(validations -> {
                    List<ErrorDetail> errors = new ArrayList<>();
                    ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_EMAIL);
                    ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_NAME);
                    ValidatorUtilApi.validateField(validations.getT3(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_PASSWORD);
                    ValidatorUtilApi.validateField(validations.getT4(), errors, TechnicalMessage.FIELD_INVALID_ARRAY_PHONES);
                    ValidatorUtilApi.validateField(validations.getT5(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_FORMAT_EMAIL);
                    return errors;
                });
    }

    private static Mono<List<ErrorDetail>> validateStructureBodyAuth(GenerateTokenRequestApi generateTokenRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotNull(generateTokenRequestApi.getUsername()),
                ValidatorUtil.isValidNotBlank(generateTokenRequestApi.getUsername())
        ).map(validations -> {
            List<ErrorDetail> errors = new ArrayList<>();
            ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            return errors;
        });
    }

}
