package org.sobngwi.exam.ms.elast.dao;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.sobngwi.exam.ms.elast.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * JSON tests for {@link ExamDaoImpl}.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ExamDaoImplTest {


    //@SpyBean
    @Autowired
    private ExamDaoImpl examDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SoftAssertions softly;

    @Before
    public void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    public void assertMockAndSpyAreCreated() {
        assertThat(examDao).isNotNull();
    }

    @Test
    public void getQuestionById() {
        final Map<String, Object> questionById = examDao.getQuestionById("1");

        softly.assertThat(questionById).isNotEmpty();
        softly.assertThat(questionById.keySet().toString()).isEqualTo("[1]");
        softly.assertThat(questionById.values().toString()).contains("Success");
        softly.assertThat(questionById.values().toString()).contains("Failure");
        softly.assertAll();
    }

    @Test
    public void searchQuestionById_functionalId() {
        final Map<String, Object> questionByFunctionalIdId = examDao.searchQuestionByQuestionId("ch3-q20");

        softly.assertThat(questionByFunctionalIdId).isNotEmpty();
        softly.assertThat(questionByFunctionalIdId.keySet().size()).isEqualTo(1);
        softly.assertThat(questionByFunctionalIdId.values().toString()).contains(("id=ch3-q20"));
        softly.assertThat(questionByFunctionalIdId.values().toString()).contains("chapitre=3");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap1() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("1");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(21);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch1-q1", "ch1-q2", "ch1-q3", "ch1-q4", "ch1-q5",
                "ch1-q6", "ch1-q7", "ch1-q8", "ch1-q9", "ch1-q10",
                "ch1-q11", "ch1-q12", "ch1-q13", "ch1-q14", "ch1-q15",
                "ch1-q16", "ch1-q17", "ch1-q18", "ch1-q19", "ch1-q20",
                "ch1-q21");
        softly.assertAll();
    }


    @Test
    public void searchQuestionsByChapterId_Chap2() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("2");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch2-q1", "ch2-q2", "ch2-q3", "ch2-q4", "ch2-q5",
                "ch2-q6", "ch2-q7", "ch2-q8", "ch2-q9", "ch2-q10",
                "ch2-q11", "ch2-q12", "ch2-q13", "ch2-q14", "ch2-q15",
                "ch2-q16", "ch2-q17", "ch2-q18", "ch2-q19", "ch2-q20");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap3() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("3");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(25);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch3-q1", "ch3-q2", "ch3-q3", "ch3-q4", "ch3-q5",
                "ch3-q6", "ch3-q7", "ch3-q8", "ch3-q9", "ch3-q10",
                "ch3-q11", "ch3-q12", "ch3-q13", "ch3-q14", "ch3-q15",
                "ch3-q16", "ch3-q17", "ch3-q18", "ch3-q19", "ch3-q20",
                "ch3-q21", "ch3-q22", "ch3-q23", "ch3-q24", "ch3-q25");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap4() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("4");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch4-q1", "ch4-q2", "ch4-q3", "ch4-q4", "ch4-q5",
                "ch4-q6", "ch4-q7", "ch4-q8", "ch4-q9", "ch4-q10",
                "ch4-q11", "ch4-q12", "ch4-q13", "ch4-q14", "ch4-q15",
                "ch4-q16", "ch4-q17", "ch4-q18", "ch4-q19", "ch4-q20");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap5() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("5");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch5-q1", "ch5-q2", "ch5-q3", "ch5-q4", "ch5-q5",
                "ch5-q6", "ch5-q7", "ch5-q8", "ch5-q9", "ch5-q10",
                "ch5-q11", "ch5-q12", "ch5-q13", "ch5-q14", "ch5-q15",
                "ch5-q16", "ch5-q17", "ch5-q18", "ch5-q19", "ch5-q20");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap6() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("6");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch6-q1", "ch6-q2", "ch6-q3", "ch6-q4", "ch6-q5",
                "ch6-q6", "ch6-q7", "ch6-q8", "ch6-q9", "ch6-q10",
                "ch6-q11", "ch6-q12", "ch6-q13", "ch6-q14", "ch6-q15",
                "ch6-q16", "ch6-q17", "ch6-q18", "ch6-q19", "ch6-q20");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap7() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("7");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(22);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch7-q1", "ch7-q2", "ch7-q3", "ch7-q4", "ch7-q5",
                "ch7-q6", "ch7-q7", "ch7-q8", "ch7-q9", "ch7-q10",
                "ch7-q11", "ch7-q12", "ch7-q13", "ch7-q14", "ch7-q15",
                "ch7-q16", "ch7-q17", "ch7-q18", "ch7-q19", "ch7-q20", "ch7-q21", "ch7-q22");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap8() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("8");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(23);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch8-q1", "ch8-q2", "ch8-q3", "ch8-q4", "ch8-q5",
                "ch8-q6", "ch8-q7", "ch8-q8", "ch8-q9", "ch8-q10",
                "ch8-q11", "ch8-q12", "ch8-q13", "ch8-q14", "ch8-q15",
                "ch8-q16", "ch8-q17", "ch8-q18", "ch8-q19", "ch8-q20", "ch8-q21", "ch8-q22", "ch8-q23");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap9() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("9");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch9-q1", "ch9-q2", "ch9-q3", "ch9-q4", "ch9-q5",
                "ch9-q6", "ch9-q7", "ch9-q8", "ch9-q9", "ch9-q10",
                "ch9-q11", "ch9-q12", "ch9-q13", "ch9-q14", "ch9-q15",
                "ch9-q16", "ch9-q17", "ch9-q18", "ch9-q19", "ch9-q20");
        softly.assertAll();
    }

    @Test
    public void searchQuestionsByChapterId_Chap10() {

        final Map<String, Object> stringObjectMap = examDao.searchQuestionsByChapterId("10");

        softly.assertThat(stringObjectMap.keySet().size()).isEqualTo(20);
        softly.assertThat(stringObjectMap.values().toString()).contains(
                "ch10-q1", "ch10-q2", "ch10-q3", "ch10-q4", "ch10-q5",
                "ch10-q6", "ch10-q7", "ch10-q8", "ch10-q9", "ch10-q10",
                "ch10-q11", "ch10-q12", "ch10-q13", "ch10-q14", "ch10-q15",
                "ch10-q16", "ch10-q17", "ch10-q18", "ch10-q19", "ch10-q20");
        softly.assertAll();
    }

    @Test
    public void searchAllChaptersQuestions() {

        final Map<String, Object> allChaptersQuestions = examDao.searchAllQuestionsByExamType("N");

        softly.assertThat(allChaptersQuestions.keySet().size()).isEqualTo(211);
        softly.assertAll();
    }

    @Test
    public void searchAllExamQuestions() {
        thrown.expect(QuestionNotFoundException.class);
        thrown.expectMessage("No Question Found For search Criteria :Y");

        final Map<String, Object> allExamsQuestions = examDao.searchAllQuestionsByExamType("Y");

        softly.assertThat(allExamsQuestions.keySet().size()).isEqualTo(0);
        softly.assertAll();
    }

    @Test
    public void getForNotExistingQuestionShouldThrowException() {
        thrown.expect(QuestionNotFoundException.class);
        thrown.expectMessage("No Question Found For search Criteria :" + 1000);

        examDao.getQuestionById("1000");
    }

    @Test
    public void searchForNotExistingQuestionShouldThrowException() {
        thrown.expect(QuestionNotFoundException.class);
        thrown.expectMessage("No Question Found For search Criteria :" + 1000);

        examDao.searchQuestionByQuestionId("1000");
    }
}