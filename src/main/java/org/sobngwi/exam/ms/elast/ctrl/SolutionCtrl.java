package org.sobngwi.exam.ms.elast.ctrl;


import lombok.extern.slf4j.Slf4j;
import org.sobngwi.exam.ms.elast.exceptions.QuestionNotFoundException;
import org.sobngwi.exam.ms.elast.exceptions.TechnicalQuestionException;
import org.sobngwi.exam.ms.elast.service.ExamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solution")
@Slf4j
@CrossOrigin(maxAge = 3600)
public class SolutionCtrl {

    private ExamServiceImpl examService;

    public SolutionCtrl(ExamServiceImpl examService) {
        this.examService = examService;
    }

    @GetMapping("/search/{solutionId}")
    public  Object  searchQuestionById(@PathVariable String solutionId){
        Assert.isTrue( (solutionId != null && !solutionId.isEmpty()) , "Solution Id should be greater than zero");
        return examService.searchSolutionByFunctionalId(solutionId).get();
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
