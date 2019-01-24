package org.sobngwi.exam.ms.elast.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.sobngwi.exam.ms.elast.exceptions.QuestionNotFoundException;
import org.sobngwi.exam.ms.elast.exceptions.TechnicalQuestionException;
import org.sobngwi.exam.ms.elast.service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @WebMvcTest} based tests for {@link QuestionCtrl}.
 */

@Slf4j
@WebMvcTest(SolutionCtrl.class)
@RunWith(SpringRunner.class)
public class SolutionCtrlTest implements  UtilsTest{

    @MockBean
    private ExamServiceImpl examService;

    @Autowired
    private MockMvc mvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void searchQuestionById() throws  Exception{
        Object object = mapToObject("solution-ch1-q1.json");

        given(examService.searchSolutionByFunctionalId("ch1-q1")).willReturn(Optional.of(object));

        mvc.perform(get("/solution/search/ch1-q1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Based on the equals() method in the code")))
                .andExpect(content().string(containsString("ch1-q1")));
    }

}