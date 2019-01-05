package org.sobngwi.exam.ms.elast.service;


import org.sobngwi.exam.ms.elast.dao.ExamDaoImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExamServiceImpl implements ExamService {

   private ExamDaoImpl examDao;

    public ExamServiceImpl(ExamDaoImpl examDao) {
        this.examDao = examDao;
    }

    @Override
    public Map<String, Object> getQuestionById(String id) {
           return   examDao.getQuestionById(id);
    }

    @Override
    public Map<String, Object> searchQuestionsByFunctionalId(String questionId) {

           return  examDao.searchQuestionByQuestionId(questionId);
    }

    @Override
    public Map<String, Object> searchQuestionsByChapterId(String chapterId) {

         return   examDao.searchQuestionsByChapterId(chapterId);

    }
}
