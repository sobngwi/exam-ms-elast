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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class ExamDaoImpl implements ExamDao {

    private static final Logger log = getLogger(ExamDaoImpl.class);

    @Value("${questionIndexName}")
    private String indexName;
    @Value("${questionIndexType}")
    private String indexType;
    private RestHighLevelClient restHighLevelClient;

    public ExamDaoImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Map<String, Object> getQuestionById(String id) throws IOException {
        final GetRequest getRequest = new GetRequest(indexName, indexType, id);
        final GetResponse getResponse = getDocumentField(id, getRequest)
                .orElseThrow(() -> new IOException("IOException : Can not Get Response"));

        Map<String, Object> result = new HashMap<>();
        result.putIfAbsent(getResponse.getId(), getResponse.getSourceAsMap().get("question"));
        return Collections.unmodifiableMap(result);
    }

    @Override
    public Map<String, Object> searchQuestionsByChapterId(String chapterId) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        QueryBuilder matchQueryBuilder = QueryBuilders
                .matchQuery("question.chapitre", chapterId);
        searchRequest.source(buildTheSearch(matchQueryBuilder, 25));

        return computeResult(chapterId, searchRequest);
    }

    @Override
    public Map<String, Object> searchAllQuestionsByExamType(String isExamQuestion) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("question.exam", isExamQuestion);

        searchRequest.source(buildTheSearch(matchQueryBuilder, 300));

        return computeResult(isExamQuestion, searchRequest);
    }


    private Optional<GetResponse> getDocumentField(String id, GetRequest getRequest) throws IOException {

        try {
            return Optional.ofNullable(restHighLevelClient.
                    get(getRequest, RequestOptions.DEFAULT));
        } catch (IOException e) {
            log.error("Unsuccessful  Reception of docs for chapId ={}, error message = [{}]", id, e.getLocalizedMessage());
            throw e;
        }
    }

    private Map<String, Object> computeResult(String chapterId, SearchRequest searchRequest) throws IOException {
        final Map<String, Object> results = new HashMap<>();
        SearchResponse searchResponse = getSearchResponse(searchRequest).orElseThrow(() -> new IOException("IOException : Can not Get Response"));

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            results.putIfAbsent(hit.getId(), hit.getSourceAsMap().get("question"));
        }
        log.info("Nb Of questions on chap  {} : {} ", chapterId, results.keySet().size());
        return Collections.unmodifiableMap(results);
    }

    private Optional<SearchResponse> getSearchResponse(SearchRequest searchRequest) throws IOException {
        try {
            return Optional.ofNullable(restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT));
        } catch (IOException e) {
            log.error("Unsuccessful  Reception of docs : error message = [{}]", e.getMessage());
            throw e;
        }

    }


    private SearchSourceBuilder buildTheSearch(QueryBuilder matchQueryBuilder, int searchResultLimit) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(searchResultLimit);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        return searchSourceBuilder;
    }

}
