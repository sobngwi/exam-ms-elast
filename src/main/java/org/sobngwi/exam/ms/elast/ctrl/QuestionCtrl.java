package org.sobngwi.exam.ms.elast.ctrl;



import org.sobngwi.exam.ms.elast.service.ExamServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/question")
public class QuestionCtrl {

    private ExamServiceImpl examService;

    public QuestionCtrl(ExamServiceImpl examService) {
        this.examService = examService;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getQuestionbyId(@PathVariable String id){
        return examService.getQuestionById(id) ;
    }

    @GetMapping("/search/{chapId}")
    public  Map<String, Object>  getQuestionsbyChapId(@PathVariable String chapId){
        return examService.searchQuestionsByChapterId(chapId) ;
    }

}
