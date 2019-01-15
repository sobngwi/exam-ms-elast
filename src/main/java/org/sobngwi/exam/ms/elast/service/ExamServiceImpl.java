package org.sobngwi.exam.ms.elast.service;


import org.sobngwi.exam.ms.elast.dao.ExamDaoImpl;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExamServiceImpl implements ExamService {

   private ExamDaoImpl examDao;

    public ExamServiceImpl(ExamDaoImpl examDao) {
        this.examDao = examDao;
    }

    @Override
    public  Optional<Object> getQuestionById(String id) {

       return  Optional.of
               (examDao.getQuestionById(id).values().stream().findFirst()).orElse(Optional.empty());

    }

    @Override
    public Optional<Object> searchQuestionsByFunctionalId(String questionId) {
           return  Optional.of
                   (examDao.searchQuestionByQuestionId(questionId).values().stream().findFirst());
    }

    @Override
    public Optional <Object> searchQuestionsByChapterId(String chapterId) {

        return   Optional.of
                (examDao.searchQuestionsByChapterId(chapterId).values().stream().collect(Collectors.toList()));

    }
}
