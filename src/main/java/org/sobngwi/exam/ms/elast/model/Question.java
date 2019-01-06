package org.sobngwi.exam.ms.elast.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@JsonRootName(value = "question")
@Data
public final class Question {

    @JsonProperty
    private String id;
    @JsonProperty
    private String exam;
    @JsonProperty
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
