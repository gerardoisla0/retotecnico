package com.seek.retotecnico.helper.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.seek.retotecnico.model.util.enums.Operation;

import java.time.LocalDateTime;
import java.util.UUID;

class ValidatorUtilTest {

    private String operation;

    @BeforeEach
    void setUp() {
        operation = Operation.CUSTOMER.getName();
    }

    @Test
    void shouldReturnTrueOnIsValidField() {
        Mono<Boolean> response = ValidatorUtil.isValidField(Operation.CUSTOMER.getName(), operation);

        StepVerifier.create(response)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnValidNotBlank() {
        Mono<Boolean> response = ValidatorUtil.isValidNotBlank(
                UUID.randomUUID().toString());

        StepVerifier.create(response)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnValidNotNull() {

        LocalDateTime dateTime = LocalDateTime.now();
        Mono<Boolean> response = ValidatorUtil.isValidNotNull(dateTime);
        StepVerifier.create(response)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnTrueIfNotNullAndNotBlank() {
        String field = "Valid Field";
        Mono<Boolean> response = ValidatorUtil.isValidNotBlankAndNotNull(field);

        StepVerifier.create(response)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnTrueIfArrayIsValid() {
        Integer[] array = {1, 2, 3};
        Mono<Boolean> response = ValidatorUtil.isValidArray(array);

        StepVerifier.create(response)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnFalseIfArrayIsEmpty() {
        Integer[] array = {};
        Mono<Boolean> response = ValidatorUtil.isValidArray(array);

        StepVerifier.create(response)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldReturnFalseIfArrayIsNull() {
        Integer[] array = null;
        Mono<Boolean> response = ValidatorUtil.isValidArray(array);

        StepVerifier.create(response)
                .expectNext(false)
                .verifyComplete();
    }


}
