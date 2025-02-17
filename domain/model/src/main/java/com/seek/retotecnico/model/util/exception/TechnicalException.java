package com.seek.retotecnico.model.util.exception;

import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class TechnicalException extends UserManagerException {

    public TechnicalException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }

    public TechnicalException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause, technicalMessage);
    }

}
