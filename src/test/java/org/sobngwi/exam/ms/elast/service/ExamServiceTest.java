package org.sobngwi.exam.ms.elast.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface ExamServiceTest {

    default   Optional<Object> getObjectForJsonInputStream(InputStream resourceAsStream , Class clazz) {
        try {
            return  Optional.ofNullable(new ObjectMapper().readValue(resourceAsStream, clazz));
        } catch (IOException e) {
            System.err.println("exception is :" +  e.getLocalizedMessage());
        }
        return Optional.empty() ;
    }
}
