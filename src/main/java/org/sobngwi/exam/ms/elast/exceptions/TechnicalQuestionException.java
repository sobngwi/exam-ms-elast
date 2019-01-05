package org.sobngwi.exam.ms.elast.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class TechnicalQuestionException extends RuntimeException {


    public TechnicalQuestionException(String message){
        super(message);
    }
}
