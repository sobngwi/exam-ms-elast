package org.sobngwi.exam.ms.elast.dao;

import java.io.IOException;
import java.util.Map;

public interface ExamDao {
    Map<String, Object> getQuestionById(String id) throws IOException;

    Map<String, Object> searchQuestionsByChapterId(String chapterId) throws IOException;

    Map<String, Object> searchAllQuestionsByExamType(String isExamQuestion) throws IOException;
}
