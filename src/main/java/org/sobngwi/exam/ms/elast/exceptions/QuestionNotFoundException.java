package org.sobngwi.exam.ms.elast.exceptions;

import lombok.Getter;

@Getter
public class QuestionNotFoundException extends RuntimeException {
    private String idQuestion;


    public QuestionNotFoundException(String idQuestion){
        super(idQuestion);
        this.idQuestion=idQuestion;
    }
}
