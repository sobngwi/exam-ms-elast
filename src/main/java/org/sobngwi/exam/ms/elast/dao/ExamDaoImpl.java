package org.sobngwi.exam.ms.elast.dao;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.sobngwi.exam.ms.elast.service.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class ExamDaoImpl implements ExamDao {

    private static final Logger log = getLogger(ExamServiceImpl.class);

    @Value("${questionIndexName}")
    private  String indexName;
    @Value("${questionIndexType}")
    private  String indexType;
    private RestHighLevelClient restHighLevelClient;

    public ExamDaoImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Map<String, Object> getQuestionById(String id){
        GetRequest getRequest = new GetRequest(indexName, indexType, id);
        GetResponse getResponse = null;

        try {
            getResponse = restHighLevelClient.
                    get(getRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            log.error("Unsuccessfull  Reception of docs for chapId ={}, error message = [{}]", id, e.getLocalizedMessage());
        }

        Map<String, Object> result = new HashMap<>();
        result.putIfAbsent(getResponse.getId(),getResponse.getSourceAsMap().get("question"));
        return Collections.unmodifiableMap(result);
    }

    @Override
    public  Map<String, Object> searchQuestionsByChapterId(String chapterId){
        final Map<String, Object> results = new HashMap<>();
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQueryBuilder = QueryBuilders
                .matchQuery("question.chapitre",  chapterId);

        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(25);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null  ;
        try {
            searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Unsuccessfull  Reception of docs for chapId ={}, error message = [{}]", chapterId, e.getMessage());
        }

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            results.putIfAbsent(hit.getId(), hit.getSourceAsMap().get("question"));
        }
        log.info("Nb Of question on chap  {} : {} ", chapterId, results.keySet().size());
        return Collections.unmodifiableMap(results);
    }

}
