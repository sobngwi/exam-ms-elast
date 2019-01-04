package org.sobngwi.exam.ms.elast.dao;

import java.util.Map;

public interface ExamDao {
    Map<String, Object> getQuestionById(String id);

    Map<String, Object> searchChapters(String chapterId);
}
