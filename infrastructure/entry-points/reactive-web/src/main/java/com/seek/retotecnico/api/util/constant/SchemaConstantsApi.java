package com.seek.retotecnico.api.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SchemaConstantsApi {

    // Request
    public static final String REQUEST_TITLE = "Estructura de la petición";

    // Response
    public static final String REQUEST_NAME = "Contiene el nombre del cliente en la petición";
    public static final String REQUEST_LAST_NAME = "Contiene el apellido del cliente en la petición";
    public static final String REQUEST_DOCUMENT_ID = "Contiene el número de documento del cliente en la petición";
    public static final String REQUEST_AGE = "Contiene la edad del cliente en la petición";
    public static final String REQUEST_BIRTHDAY = "Contiene la fecha de cumpleaños del cliente en la petición";
    public static final String REQUEST_SAVE_CUSTOMER = "Contiene la estructura de petición para guardar el cliente";

    public static final String RESPONSE_TITLE = "Estructura de la respuesta";
    public static final String RESPONSE_ID = "Contiene el identificador del cliente generado por Banco en la respuesta";
    public static final String RESPONSE_DATE_CREATED = "Contiene la fecha de creación del cliente en la respuesta";
    public static final String RESPONSE_TOKEN = "Contiene el token de autenticación del cliente en la respuesta";
    public static final String RESPONSE_MESSAGE = "Contiene el mensaje de negocio en la respuesta";

    public static final String RESPONSE_SAVE_CUSTOMER = "Contiene la estructura de respuesta para guardar el cliente";
    public static final String RESPONSE_LIST_CUSTOMER = "Contiene la estructura de respuesta para listar clientes";
    public static final String RESPONSE_CUSTOMER_METRICS = "Contiene la estructura de respuesta para obtener metricas";

    public static final String REQUEST_USER_NAME = "Contiene el usuario en la petición";
}