package org.sobngwi.exam.ms.elast.dao;

import java.util.Map;

public interface ExamDao {
    Map<String, Object> getQuestionById(String id);

    Map<String, Object> searchQuestionByQuestionId(String questionId);

    Map<String, Object> searchQuestionsByChapterId(String chapterId);

    Map<String, Object> searchAllQuestionsByExamType(String isExamQuestion);
}
