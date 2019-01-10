package org.sobngwi.exam.ms.elast.dao;

import java.util.Map;

public interface ExamDao {
    Map<String, Object> getQuestionById(String id);

    Map<String, Object> searchQuestionByQuestionId(String questionId);

    Map<String, Object> searchQuestionsByChapterId( String chapterId);

    Map<String, Object> searchAllQuestionsByExamType( String isExamQuestion);

    Map<String, Object> getSolutionById(String id);

    Map<String, Object> searchSolutionByQuestionId(String questionId);

    Map<String, Object> searchSolutionsByChapterId(String chapterId);

    Map<String, Object> searchAllSolutionsByExamType(String isExamQuestion);
}
