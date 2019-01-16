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
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code @WebMvcTest} based tests for {@link QuestionCtrl}.
 */

@Slf4j
@WebMvcTest(QuestionCtrl.class)
@RunWith(SpringRunner.class)
public class QuestionCtrlTest {

    @MockBean
    private ExamServiceImpl examService;

    @Autowired
    private MockMvc mvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void getQuestionById() throws Exception{
        Object object = mapToObject("response_q1.json");
        given(examService.getQuestionById("1")).willReturn(Optional.of(object));

        mvc.perform(get("/question/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hashCode()")))
                .andExpect(content().string(containsString("ch1-q1")));
    }


    @Test
    public void searchQuestionsByChapId() throws Exception {
        Object object = mapToObject("response_ch1.json");
        given(examService.searchQuestionsByChapterId("1")).willReturn(Optional.of(object));

        mvc.perform(get("/question/search/chapter/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ch1-q1")))
                .andExpect(content().string(containsString("ch1-q2")))
                .andExpect(content().string(containsString("ch1-q3")))
                .andExpect(content().string(containsString("ch1-q10")))
                .andExpect(content().string(containsString("ch1-q19")))
                .andExpect(content().string(containsString("ch1-q20")))
                .andExpect(content().string(containsString("ch1-q21")));
    }

    @Test
    public void searchQuestionById() throws  Exception{
        Object object = mapToObject("response_q1.json");

        given(examService.searchQuestionsByFunctionalId("1")).willReturn(Optional.of(object));

        mvc.perform(get("/question/search/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hashCode()")))
                .andExpect(content().string(containsString("ch1-q1")));
    }

    @Test
    public void searchSubjects() throws  Exception{
        Object object = mapToObject("response_subjects.json");
        given(examService.searchAllSubjectNamesInQuestions()).willReturn(Arrays.asList(object));

        mvc.perform(get("/question/search/subjects/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hashCode()")))
                .andExpect(content().string(containsString("ch1-q1")))
                .andExpect(content().string(containsString("ch2-q2")))
                .andExpect(content().string(containsString("ch3-q3")))
                .andExpect(content().string(containsString("ch4-q4")))
                .andExpect(content().string(containsString("ch5-q5")))
                .andExpect(content().string(containsString("ch6-q6")))
                .andExpect(content().string(containsString("ch7-q7")))
                .andExpect(content().string(containsString("ch8-q8")))
                .andExpect(content().string(containsString("ch9-q9")))
                .andExpect(content().string(containsString("ch9-q9")))
                .andExpect(content().string(containsString("ch10-q10")));
    }

    @Test
    public void shouldThrowsQuestionNotFoundExeption() throws  Exception{

        given(examService.searchQuestionsByFunctionalId("1")).willThrow(QuestionNotFoundException.class);

        mvc.perform(get("/question/search/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowsTechnicalQuestionException() throws  Exception{

        given(examService.searchQuestionsByFunctionalId("1")).willThrow( new TechnicalQuestionException("IO Problem."));
        mvc.perform(get("/question/search/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    private Object  mapToObject(String fileName ){
        ObjectMapper mapper = new ObjectMapper();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            return  mapper.readValue(file, Object.class);
        } catch (IOException e) {
            log.error("IO on file load: {}", e.getLocalizedMessage());
        }
        return "";
    }
}