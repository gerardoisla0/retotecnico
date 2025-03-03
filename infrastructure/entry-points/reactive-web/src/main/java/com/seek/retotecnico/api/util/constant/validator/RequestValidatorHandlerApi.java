package com.seek.retotecnico.api.util.constant.validator;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.seek.retotecnico.api.util.validator.ValidatorUtilApi;
import com.seek.retotecnico.helper.validator.ValidatorUtil;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.*;

@UtilityClass
public class RequestValidatorHandlerApi {

    public static Mono<List<ErrorDetail>> validateRequest(SeekRequestApi createCustomerRequestApi) {
        return validateRequestStructure(createCustomerRequestApi)
                .filter(isValidRequest -> isValidRequest)
                .flatMap(isValidRequest -> validateStructureBody(createCustomerRequestApi))
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    public static Mono<List<ErrorDetail>> validateRequestTask(SeekRequestApi createCustomerRequestApi) {
        return validateRequestStructureTask(createCustomerRequestApi)
                .filter(isValidRequest -> isValidRequest)
                .flatMap(isValidRequest -> validateStructureBodyTask(createCustomerRequestApi))
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    public static Mono<List<ErrorDetail>> validateRequestTaskUpdate(SeekRequestApi updateCustomerRequestApi) {
        return validateRequestStructureTaskUpdate(updateCustomerRequestApi)
                .filter(isValidRequest -> isValidRequest)
                .flatMap(isValidRequest -> validateStructureBodyTaskUpdate(updateCustomerRequestApi))
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    public static Mono<List<ErrorDetail>> validateRequestAuth(SeekRequestApi seekRequestApi) {
        return Mono.just(seekRequestApi)
                .flatMap(RequestValidatorHandlerApi::validateStructureBodyAuth)
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(ValidatorUtilApi.buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    private static Mono<Boolean> validateRequestStructure(SeekRequestApi saveUserRequestApi) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(saveUserRequestApi)
                && Objects.nonNull(saveUserRequestApi.getSaveUserRQ().getName())
                && Objects.nonNull(saveUserRequestApi.getSaveUserRQ().getEmail())
                && Objects.nonNull(saveUserRequestApi.getSaveUserRQ().getPassword())));
    }

    private static Mono<Boolean> validateRequestStructureTask(SeekRequestApi saveTaskRequestApi) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(saveTaskRequestApi)
                && Objects.nonNull(saveTaskRequestApi.getSaveTaskRQ().getTitle())
                && Objects.nonNull(saveTaskRequestApi.getSaveTaskRQ().getDescription())
                && Objects.nonNull(saveTaskRequestApi.getSaveTaskRQ().getStatus())));
    }

    private static Mono<Boolean> validateRequestStructureTaskUpdate(SeekRequestApi updateTaskRequestApi) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(updateTaskRequestApi)
                && Objects.nonNull(updateTaskRequestApi.getUpdateTaskRQ().getTitle())
                && Objects.nonNull(updateTaskRequestApi.getUpdateTaskRQ().getDescription())
                && Objects.nonNull(updateTaskRequestApi.getUpdateTaskRQ().getStatus())));
    }

    private static Mono<List<ErrorDetail>> validateStructureBody( SeekRequestApi createCustomerRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveUserRQ().getName()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveUserRQ().getEmail()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createCustomerRequestApi.getSaveUserRQ().getPassword())
                ).map(validations -> {
                    List<ErrorDetail> errors = new ArrayList<>();
                    ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_NAME);
                    ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_EMAIL);
                    ValidatorUtilApi.validateField(validations.getT3(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_PASSWORD);
                    return errors;
                });
    }

    private static Mono<List<ErrorDetail>> validateStructureBodyTask( SeekRequestApi createTaskRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotBlankAndNotNull(createTaskRequestApi.getSaveTaskRQ().getTitle()),
                ValidatorUtil.isValidNotBlankAndNotNull(createTaskRequestApi.getSaveTaskRQ().getDescription()),
                ValidatorUtil.isValidNotBlankAndNotNull(createTaskRequestApi.getSaveTaskRQ().getStatus())
        ).map(validations -> {
            List<ErrorDetail> errors = new ArrayList<>();
            ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_NAME);
            ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_EMAIL);
            ValidatorUtilApi.validateField(validations.getT3(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_PASSWORD);
            return errors;
        });
    }

    private static Mono<List<ErrorDetail>> validateStructureBodyTaskUpdate( SeekRequestApi updateTaskRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotBlankAndNotNull(updateTaskRequestApi.getUpdateTaskRQ().getTitle()),
                ValidatorUtil.isValidNotBlankAndNotNull(updateTaskRequestApi.getUpdateTaskRQ().getDescription()),
                ValidatorUtil.isValidNotBlankAndNotNull(updateTaskRequestApi.getUpdateTaskRQ().getStatus())
        ).map(validations -> {
            List<ErrorDetail> errors = new ArrayList<>();
            ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_NAME);
            ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_EMAIL);
            ValidatorUtilApi.validateField(validations.getT3(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_PASSWORD);
            return errors;
        });
    }

    private static Mono<List<ErrorDetail>> validateStructureBodyAuth(SeekRequestApi seekRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotNull(seekRequestApi.getAuthUserRQ().getEmail()),
                ValidatorUtil.isValidNotBlank(seekRequestApi.getAuthUserRQ().getPassword())
        ).map(validations -> {
            List<ErrorDetail> errors = new ArrayList<>();
            ValidatorUtilApi.validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            ValidatorUtilApi.validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            return errors;
        });
    }

}
