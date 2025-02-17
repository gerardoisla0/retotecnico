package com.seek.retotecnico.model.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {
    FIELD_INVALID_REQUEST_STRUCTURE("¡Ups! Lo sentimos la petición no es valida"),
    FIELD_INVALID_REQUEST_EMAIL("¡Ups! Lo sentimos el correo del cliente en la petición no es valida"),
    FIELD_INVALID_REQUEST_NAME("¡Ups! Lo sentimos el nombre del cliente en la petición no es valida"),
    FIELD_INVALID_REQUEST_PASSWORD("¡Ups! Lo sentimos la contraseña del cliente en la petición no es valida"),
    ERROR_SERVICE_UNAVAILABLE("¡Ups! Estamos presentado una falla, nos encontramos trabajando para encontrar una solución lo antes posible. Porfa inténtalo más tarde."),
    ERROR_INTERNAL_SERVER("¡Ups! Algo fallo, porfa vuelva a intentarlo"),
    ERROR_BAD_REQUEST("La estructura de la petición no es la esperada"),
    ERROR_BUSINESS("¡Ups! Tenemos un problema, estamos tratando de arreglarlo."),
    SUCCESS("SUCCESS"),
    ERROR_CLIENT_EXIST("El cliente ya se encuentra registrado"),
    ERROR_TOKEN_EXPIRED("El Token está expirado o incorrecto"),
    FIELD_INVALID_REQUEST_FORMAT_EMAIL("¡Ups! Lo sentimos el correo del cliente en la petición no tiene el formato correcto"),
    FIELD_INVALID_REQUEST_FORMAT_PASSWORD("¡Ups! Lo sentimos el clave del cliente en la petición no tiene el formato correcto"),
    FIELD_INVALID_REQUEST_USERNAME("¡Ups! Lo sentimos el nombre de usuario en la petición no es valida"),
    FIELD_INVALID_ARRAY_PHONES("¡Ups! Lo sentimos debe registrar al menos un telefono de cliente en la petición no es valida"),
    FIELD_INVALID_PHONE("¡Ups! Lo sentimos la esctructura de telefono de cliente en la petición no es valida");

    private final String message;

    private static final Map<String, TechnicalMessage> BY_TECHNICAL_MESSAGE = new HashMap<>();

    static {
        for (TechnicalMessage technicalMessage : values()) {
            BY_TECHNICAL_MESSAGE.put(technicalMessage.getMessage(), technicalMessage);
        }
    }
    public static TechnicalMessage findByMessage(String message) {
        return Optional.ofNullable(message)
                .map(BY_TECHNICAL_MESSAGE::get)
                .orElse(TechnicalMessage.ERROR_INTERNAL_SERVER);
    }
}
