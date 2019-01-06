package org.sobngwi.exam.ms.elast.model;

import lombok.Data;

import java.util.List;

public class QuestionBuilder {

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

    public QuestionBuilder  setId(String id) {
        this.id = id;
        return this;
    }

    public QuestionBuilder setChapterId(String chapterId) {
        this.chapitre = chapterId;
        return this;
    }

    public QuestionBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public QuestionBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public QuestionBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public QuestionBuilder setJavaCode(String javaCode) {
        this.javaCode = javaCode;
        return this;
    }

    public QuestionBuilder setChoices(List<String> choices) {
        this.choices = choices;
        return this;
    }

    public Question  build(){


         return new Question( id, exam, chapitre, subject, type, text,
                 javaCode, choices, choice, reasons);

    }
}
