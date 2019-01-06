package org.sobngwi.exam.ms.elast.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.sobngwi.exam.ms.elast.model.Question;

import org.sobngwi.exam.ms.elast.model.QuestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JSON tests for {@link Question}.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class ValidateQuestionJsonTest {

    private ObjectMapper objectMapper;
    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        JacksonTester.initFields(this, objectMapper);
    }
    @After
    public void tearDown() throws Exception{
        objectMapper = null;
    }

    @Autowired
    private JacksonTester<Question> json;

    @Test
    public void serializeJson() throws Exception {
      /*  Question details = new Question("ch1-q1", false, "1", "Advanced Class Design Chapter",
                "Single Choice", "What is the result of the following code?", "public String firstName, lastName;",
                Arrays.asList("\"A.\": \"Success\"", " \"B.\": \"Failure\" " , "\"C.\": \"The hashCode() method fails to compile.\" "),
                "\"B.\"", "The answer needs to implement List because the scenario allows duplicates. " +
                "Since you need a List, you can eliminate C, D, and E immediately. HashMap is a Map and HashSet is a Set. " +
                "LinkedList is both a List and a Queue. You want a regular List. Option A, Arrays, is trying to distract you. " +
                "It is a utility class rather than a Collection. An array is not a collection." +
                " By process of elimination, the answer is B."); */
        QuestionBuilder questionBuilder = new QuestionBuilder();
        questionBuilder.setId("ch1-q1")
                       .setChapterId("3");
       Question
         details = questionBuilder.build();
       /* String jsonString = objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE)
                .writeValueAsString(new RootNameDemoBean());*/

        String jsonString = objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE)
                .writeValueAsString(details);
        System.err.println(jsonString);

        System.out.println(json.read("ch3-q1.json").getObject().toString());

     //   InputStream input = new FileInputStream("/Users/sobngwi/intelliJ/codebase/exam-ms-elast/src/test/resources/org/sobngwi/exam/ms/elast/json/ch3-q1.json");

//        String jsonReadFromFile = objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE).readValue(input,Question.class).toString();
      //  System.out.println(jsonReadFromFile);
        //assertThat(this.json.write(details)).isEqualTo("ch3-q1.json");
        //assertThat(this.json.write(details)).isEqualToJson("ch3-q1.json");
       // assertThat(json.write(details)).isEqualToJson("expected.json");

        assertThat(jsonString).contains("question");
        assertThat(json.write(details)).hasJsonPathStringValue("@.id");
        assertThat(json.write(details)).extractingJsonPathStringValue("@.chapitre")
                .isEqualTo("3");
    }

}

/*
public Question(String id, boolean exam, String chapterId, String subject,
                    String type, String text, String javaCode, List<String> choices,
                    String choice, String reasons) {
 */
/*
"1": {
    "exam": "N",
    "javaCode": "public class Employee { \npublic int employeeId; \npublic String firstName, lastName; \npublic int yearStarted;\n@Override public int hashCode() {\n return employeeId;\n}\npublic boolean equals(Employee e) {\nreturn this.employeeId == e.employeeId;\n}\npublic static void main(String[] args) {\n Employee one = new Employee();\none.employeeId = 101;\nEmployee two = new Employee();\ntwo.employeeId = 101; \nif (one.equals(two)) System.out.println(\"Success\"); \nelse System.out.println(\"Failure\"); \n } \n}",
    "subject": "Advanced Class Design Chapter",
    "id": "ch1-q1",
    "text": "What is the result of the following code?",
    "chapitre": "1",
    "type": "Single Choice",
    "choices": [
      {
        "A.": "Success"
      },
      {
        "B.": "Failure"
      },
      {
        "C.": "The hashCode() method fails to compile."
      },
      {
        "D.": "The equals() method fails to compile."
      },
      {
        "E.": "Another line of code fails to compile."
      },
      {
        "F.": "A runtime exception is thrown."
      },
      {
        "G.": ""
      }
    ]
  }
 */