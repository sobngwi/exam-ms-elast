package org.sobngwi.exam.ms.elast.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ExamDao {

    private final String INDEX = "java8-ocp2";
    private final String TYPE = "_doc";
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;

    public ExamDao( ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.restHighLevelClient = restHighLevelClient;
    }


    public Map<String, Object> getQuestionById(String id){
        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest);
        } catch (java.io.IOException e){
            e.getLocalizedMessage();
        }
        Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
        return sourceAsMap;
    }

}
