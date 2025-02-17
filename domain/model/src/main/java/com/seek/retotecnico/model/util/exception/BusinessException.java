package com.seek.retotecnico.model.util.exception;

import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class BusinessException extends UserManagerException {


    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }

    public BusinessException(String message, TechnicalMessage technicalMessage) {
        super(message, technicalMessage);
    }

}
