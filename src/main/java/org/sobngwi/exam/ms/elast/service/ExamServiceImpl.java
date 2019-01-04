package org.sobngwi.exam.ms.elast.service;



import org.sobngwi.exam.ms.elast.dao.ExamDaoImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ExamServiceImpl implements ExamService {

   private ExamDaoImpl examDao;

    public ExamServiceImpl(ExamDaoImpl examDao) {
        this.examDao = examDao;
    }

    @Override
    public Map<String, Object> getQuestionById(String id) {
        Map<String, Object> questionById = null;
        try {
            questionById = examDao.getQuestionById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionById;
    }

    @Override
    public Map<String, Object> searchQuestionsByChapterId(String chapterId) {
        Map<String, Object> questionBChapterId = null;

        try {
            questionBChapterId = examDao.searchQuestionsByChapterId(chapterId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionBChapterId ;
    }
}
