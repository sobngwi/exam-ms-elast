package org.sobngwi.exam.ms.elast.ctrl;


import org.sobngwi.exam.ms.elast.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionCtrl {


    private ExamDao examDao ;

    public QuestionCtrl(ExamDao examDao) {
        this.examDao = examDao;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getQuestionbyId(@PathVariable String id){
        return examDao.getQuestionById(id) ;
    }
}
