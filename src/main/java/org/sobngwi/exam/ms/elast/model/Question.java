package org.sobngwi.exam.ms.elast.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public final class Question {

    private String id;
    private String exam;
    private String chapitre;
    private String subject;
    private String type;
    private String text;
    private String javaCode;
    private List<String> choices;
    private String choice;
    private String reasons;

    public Question(String id, String exam, String chapterId, String subject,
                    String type, String text, String javaCode, List<String> choices,
                    String choice, String reasons) {
        this.id = id;
        this.exam = exam;
        this.chapitre = chapterId;
        this.subject = subject;
        this.type = type;
        this.text = text;
        this.javaCode = javaCode;
        this.choices = choices;
        this.choice = choice;
        this.reasons = reasons;
    }

    public Question() {
    }

}
