package org.sobngwi.exam.ms.elast.service;

import java.io.IOException;
import java.util.Map;

public interface ExamService {

    Map<String, Object> getQuestionById(String id) ;

    Map<String, Object> searchQuestionsByChapterId(String chapterId);
}
