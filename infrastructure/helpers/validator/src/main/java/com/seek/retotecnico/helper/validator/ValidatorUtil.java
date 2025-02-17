package com.seek.retotecnico.helper.validator;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ValidatorUtil {

    public static Mono<Boolean> isValidField(String expected, String actual) {
        return Mono.defer(() -> Mono.just(StringUtils.isNotBlank(expected) &&
                StringUtils.isNotBlank(actual) &&
                expected.equals(actual))
        );
    }

    public static Mono<Boolean> isValidNotBlank(String field) {
        return Mono.defer(() -> Mono.just(StringUtils.isNotBlank(field)));
    }

    public static Mono<Boolean> isValidNotNull(Object object) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(object)));
    }

    public static Mono<Boolean> isValidNotBlankAndNotNull(String field) {
        return Mono.zip(
                        isValidNotNull(field),
                        isValidNotBlank(field)
                )
                .map(tuple -> tuple.getT1() && tuple.getT2());
    }

    public static <T> Mono<Boolean> isValidArray(T[] array) {
        return Mono.defer(() -> Mono.just(array != null && array.length > 0));
    }


}
