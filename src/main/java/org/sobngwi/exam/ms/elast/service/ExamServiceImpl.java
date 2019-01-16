package org.sobngwi.exam.ms.elast.service;


import lombok.extern.slf4j.Slf4j;
import org.sobngwi.exam.ms.elast.dao.ExamDaoImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    public List<Object> searchAllSubjectNamesInQuestions(){
    List<Object> results = new ArrayList<>();
        for ( int index = 1 ; index <=10 ; index++){
            String functionalQuestionId="ch" +index + "-q"+ index;
            log.debug("searchin question id : {}", functionalQuestionId);
            results.add(this.searchQuestionsByFunctionalId(functionalQuestionId).get());
        }
        return results;
    }

}
