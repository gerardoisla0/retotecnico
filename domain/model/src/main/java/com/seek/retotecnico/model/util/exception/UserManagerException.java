package com.seek.retotecnico.model.util.exception;

import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class UserManagerException extends RuntimeException {

    private final TechnicalMessage technicalMessage;

    public UserManagerException(TechnicalMessage technicalMessage) {
        this(technicalMessage.getMessage(), technicalMessage);
    }

    public UserManagerException(String message, TechnicalMessage technicalMessage) {
        super(message);
        this.technicalMessage = technicalMessage;
    }

    public UserManagerException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause);
        this.technicalMessage = technicalMessage;
    }

}
