package org.sobngwi.exam.ms.elast.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExamService {

    Optional<Object> getQuestionById(String id) ;

    Optional<Object>  searchQuestionsByChapterId(String chapterId);

    Optional<Object>  searchQuestionsByFunctionalId(String chapterId);

    List<Object> searchAllSubjectNamesInQuestions();
}
