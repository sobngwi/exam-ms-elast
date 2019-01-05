package org.sobngwi.exam.ms.elast.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class QuestionNotFoundException extends RuntimeException {
    private String idQuestion;


    public QuestionNotFoundException(String idQuestion){
        super(idQuestion);
        this.idQuestion=idQuestion;
    }
}
