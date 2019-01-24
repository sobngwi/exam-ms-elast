package org.sobngwi.exam.ms.elast.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public interface UtilsTest {

    default  Object  mapToObject(String fileName ){
        ObjectMapper mapper = new ObjectMapper();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            return  mapper.readValue(file, Object.class);
        } catch (IOException e) {
            System.err.println("IO on file load: " + e.getLocalizedMessage());
        }
        return "";
    }
}
