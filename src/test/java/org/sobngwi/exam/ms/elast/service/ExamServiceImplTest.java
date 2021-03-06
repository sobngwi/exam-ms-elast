package org.sobngwi.exam.ms.elast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sobngwi.exam.ms.elast.dao.ExamDaoImpl;
import org.sobngwi.exam.ms.elast.model.Question;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * JSON tests for {@link ExamServiceImpl}.
 */
@Slf4j
public class ExamServiceImplTest implements ExamServiceTest{

    @Mock
    private ExamDaoImpl examDao;

    private ExamService examService;

    private SoftAssertions softly;
    private InputStream resourceAsStream_ch1_q1, resourceAsStream_ch3_q20, resourceAsStream_ch5, resourceAsStream_subjects;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        softly = new SoftAssertions();
        examService = new ExamServiceImpl(examDao);
        resourceAsStream_ch1_q1  = Question.class.getResourceAsStream("/response_q1.json");
        resourceAsStream_ch3_q20 = Question.class.getResourceAsStream("/response_ch3-q20.json");
        resourceAsStream_ch5     = Question.class.getResourceAsStream("/response_chap5.json");
        resourceAsStream_subjects = Question.class.getResourceAsStream("/response_subjects.json");
    }

    @Test
    public void testMockAreUp() {
        assertThat(examDao).isNotNull();
    }

    @Test
    public void getQuestionById() {
       // final InputStream resourceAsStream = Question.class.getResourceAsStream("/response_q1.json");
        Object objectForIdOne = getObjectForJsonInputStream(resourceAsStream_ch1_q1, Question.class).get();
        Map<String, Object> mapQuestionById= new HashMap<>();
        mapQuestionById.putIfAbsent("1", objectForIdOne);
        given(examDao.getQuestionById(anyString())).willReturn(mapQuestionById);

        examService.getQuestionById("1") ;

        softly.assertThat(mapQuestionById).isNotEmpty();
        softly.assertThat(mapQuestionById.keySet().toString()).isEqualTo("[1]");
        softly.assertThat(mapQuestionById.values().toString()).contains("Success");
        softly.assertThat(mapQuestionById.values().toString()).contains("Failure");
        softly.assertAll();

        verify(examDao, times(1)).getQuestionById("1");

    }


    @Test
    public void searchQuestionsByFunctionalId() {

        Object objectForIdOne = getObjectForJsonInputStream(resourceAsStream_ch3_q20, Question.class).get();
        Map<String, Object> mapQuestionFunctionalById= new HashMap<>();
        mapQuestionFunctionalById.putIfAbsent("ch3-q20", objectForIdOne);
        given(examDao.searchQuestionByQuestionId(anyString())).willReturn(mapQuestionFunctionalById);

        examService.searchQuestionsByFunctionalId("1") ;

        softly.assertThat(mapQuestionFunctionalById).isNotEmpty();
        softly.assertThat(mapQuestionFunctionalById.keySet().size()).isEqualTo(1);
        softly.assertThat(mapQuestionFunctionalById.values().toString()).contains(("What is the result of the following code?"));
        softly.assertThat(mapQuestionFunctionalById.values().toString()).contains(("Compiler error on line 7."));
        softly.assertThat(mapQuestionFunctionalById.values().toString()).contains("chapitre=3");
        softly.assertAll();

        verify(examDao, times(1)).searchQuestionByQuestionId("1");
    }

    @Test
    public void searchQuestionsByChapterId() {
        Object objectForIdOne = getObjectForJsonInputStream(resourceAsStream_ch5, Collection.class).get();
        Map<String, Object> mapQuestionChapter5= new HashMap<>();
        mapQuestionChapter5.putIfAbsent("5", objectForIdOne);
        given(examDao.searchQuestionsByChapterId(anyString())).willReturn(mapQuestionChapter5);

        examService.searchQuestionsByChapterId("5") ;

        softly.assertThat(mapQuestionChapter5.keySet().size()).isEqualTo(1);
        softly.assertThat(mapQuestionChapter5.values().toString()).contains(
                "ch5-q1", "ch5-q2", "ch5-q3", "ch5-q4", "ch5-q5",
                "ch5-q6", "ch5-q7", "ch5-q8", "ch5-q9", "ch5-q10",
                "ch5-q11", "ch5-q12", "ch5-q13", "ch5-q14", "ch5-q15",
                "ch5-q16", "ch5-q17", "ch5-q18", "ch5-q19", "ch5-q20");
        softly.assertAll();
        verify(examDao, times(1)).searchQuestionsByChapterId("5");
    }


    @Test
    public void searchAllSubjectsByChapterId() {

        Object objectForIdOne = getObjectForJsonInputStream(resourceAsStream_subjects, Collection.class).get();
        Map<String, Object> mapQuestionSubjects= new HashMap<>();
        mapQuestionSubjects.putIfAbsent("1", objectForIdOne);
        given(examDao.searchQuestionsByChapterId(anyString())).willReturn(mapQuestionSubjects);

        examService.searchAllSubjectNamesInQuestions();

        softly.assertThat(mapQuestionSubjects.keySet().size()).isEqualTo(1);
        softly.assertThat(mapQuestionSubjects.values().toString()).contains(
                "ch1-q1", "ch2-q2", "ch3-q3", "ch4-q4", "ch5-q5", "ch6-q6", "ch7-q7", "ch8-q8", "ch9-q9", "ch10-q10");
        softly.assertAll();
        verify(examDao, times(10)).searchQuestionByQuestionId(anyString());
        verify(examDao, times(1)).searchQuestionByQuestionId("ch1-q1");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch2-q2");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch3-q3");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch4-q4");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch5-q5");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch6-q6");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch7-q7");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch8-q8");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch9-q9");
        verify(examDao, times(1)).searchQuestionByQuestionId("ch10-q10");

    }

}