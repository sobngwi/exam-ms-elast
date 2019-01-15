package org.sobngwi.exam.ms.elast.ctrl;


import lombok.extern.slf4j.Slf4j;
import org.sobngwi.exam.ms.elast.exceptions.QuestionNotFoundException;
import org.sobngwi.exam.ms.elast.exceptions.TechnicalQuestionException;
import org.sobngwi.exam.ms.elast.service.ExamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@Slf4j
@CrossOrigin(maxAge = 3600)
public class QuestionCtrl {


    private ExamServiceImpl examService;

    public QuestionCtrl(ExamServiceImpl examService) {
        this.examService = examService;
    }


    @GetMapping(path="/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getQuestionBbyId(@PathVariable String id){
        Assert.isTrue(Integer.parseInt(id) > 0 , "Id should be greater than zero");
        return examService.getQuestionById(id).get();
    }

    @GetMapping("/search/chapter/{chapId}")
    public  Object  searchQuestionsByChapId(@PathVariable String chapId){
        Assert.isTrue(Integer.parseInt(chapId) > 0 , "Id should be greater than zero");
        return examService.searchQuestionsByChapterId(chapId).get() ;
    }

    @GetMapping("/search/{questionId}")
    public  Object  searchQuestionById(@PathVariable String questionId){
        Assert.isTrue( (questionId != null && !questionId.isEmpty()) , "Id should be greater than zero");
        return examService.searchQuestionsByFunctionalId(questionId).get() ;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleQuestionNotFound(QuestionNotFoundException ex) {
        log.error("Exception, " + ex.getIdQuestion());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void handleQuestionTechnicalError(TechnicalQuestionException ex) {
        log.error("Exception, " + ex.getLocalizedMessage());
    }

}
