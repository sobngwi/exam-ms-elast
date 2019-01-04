package org.sobngwi.exam.ms.elast;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.slf4j.LoggerFactory.*;

@SpringBootApplication
public class ExamMicroServiceElastApplication {

    private static final Logger log =
            getLogger(ExamMicroServiceElastApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ExamMicroServiceElastApplication.class, args);
        log.info("Starting Application ExamMicroServiceElastApplication OK");
    }

}

