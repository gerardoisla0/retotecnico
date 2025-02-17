package com.seek.retotecnico.model.exception;

import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.UserManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserManagerExceptionTest {

    @Test
    void shouldReturnInternalServerMessageOnExperianException() {

        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_INTERNAL_SERVER;

        UserManagerException exception = new UserManagerException(technicalMessage);

        assertNotNull(exception);
        Assertions.assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }

    @Test
    void shouldReturnUnauthorizedMessageOnExperianException() {

        String message = "This is a mistake";
        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_BAD_REQUEST;

        UserManagerException exception = new UserManagerException(message, technicalMessage);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        Assertions.assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }

    @Test
    void shouldReturnIllegalArgumentExceptionOnExperianException() {

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Oups error");
        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_INTERNAL_SERVER;

        UserManagerException exception = new UserManagerException(illegalArgumentException, technicalMessage);

        assertNotNull(exception);
        assertEquals(illegalArgumentException, exception.getCause());
        Assertions.assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }
}
