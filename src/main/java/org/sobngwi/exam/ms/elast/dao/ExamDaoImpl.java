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
import org.sobngwi.exam.ms.elast.exceptions.QuestionNotFoundException;
import org.sobngwi.exam.ms.elast.exceptions.TechnicalQuestionException;
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

    private final static int MAX_SEARCH_RESULT = 300;
    private final static int MAX_SEARCH_RESULT_PER_CHAPTER = 30;
    public static final int REQUEST_TIME_OUT_DURATION_IN_SECONDS = 60;
    private final static int LIMIT_FOR_SINGLE_QUESTION_RESULT = 1;

    @Value("${questionIndexName}")
    private String questionIndexName;

    @Value("${solutionIndexName}")
    private String solutionIndexName;

    @Value("${docIndexType}")
    private String indexType;

    private RestHighLevelClient restHighLevelClient;

    private static enum SearchPrefixWord{
        QUESTION("question"), QUESTION_ID("question.id"), QUESTION_CHAPTER_ID("question.chapitre"), QUESTION_EXAM("question.exam"),
        SOLUTION("solution"), SOLUTION_ID("solution.id"), SOLUTION_CHAPTER_ID("solution.chapitre");
         SearchPrefixWord(String prefix) {
            this.prefix = prefix;
        }
        private String prefix;

        public String toString(){
            return prefix;
        }
    }

    public ExamDaoImpl(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public Map<String, Object> getQuestionById(String id) {
        final GetRequest getRequest = new GetRequest(questionIndexName, indexType, id);
        return executeAndGetQuestionOrSolutionById(id, SearchPrefixWord.QUESTION.toString(),  getRequest);
    }

    @Override
    public Map<String, Object> searchQuestionByQuestionId(String questionId) {

        SearchRequest searchRequest = new SearchRequest(questionIndexName);
        return executeAndSearchQuestionOrSolutionById(SearchPrefixWord.QUESTION_ID.toString(), questionId, searchRequest);
    }

    @Override
    public Map<String, Object> searchQuestionsByChapterId(String chapterId) {

        SearchRequest searchRequest = new SearchRequest(questionIndexName);
        QueryBuilder matchQueryBuilder = QueryBuilders
                .matchPhraseQuery(SearchPrefixWord.QUESTION_CHAPTER_ID.toString(), chapterId);
        searchRequest.source(buildTheSearch(matchQueryBuilder, MAX_SEARCH_RESULT_PER_CHAPTER));

        return computeResult(SearchPrefixWord.QUESTION_ID.toString(), searchRequest, chapterId);
    }

    @Override
    public Map<String, Object> searchAllQuestionsByExamType(String isExamQuestion) {

        SearchRequest searchRequest = new SearchRequest(questionIndexName);
        return searchAllQuestionsOrSolutionsByExamType(isExamQuestion, searchRequest);
    }

    @Override
    public Map<String, Object> getSolutionById(String id) {
        final GetRequest getRequest = new GetRequest(solutionIndexName, indexType, id);
        return executeAndGetQuestionOrSolutionById(id, SearchPrefixWord.SOLUTION.toString(),  getRequest);

    }

    @Override
    public Map<String, Object> searchSolutionByQuestionId(String questionId) {
        SearchRequest searchRequest = new SearchRequest(solutionIndexName);
        return executeAndSearchQuestionOrSolutionById( SearchPrefixWord.SOLUTION_ID.toString(), questionId, searchRequest);
    }



    @Override
    public Map<String, Object> searchSolutionsByChapterId(String chapterId) {
        SearchRequest searchRequest = new SearchRequest(solutionIndexName);
        return executeAndGetQuestionOrSolutionByChapterId(SearchPrefixWord.SOLUTION_CHAPTER_ID.toString(), chapterId, searchRequest);
    }


    @Override
    public Map<String, Object> searchAllSolutionsByExamType(String isExamQuestion) {
        SearchRequest searchRequest = new SearchRequest(solutionIndexName);
        return searchAllQuestionsOrSolutionsByExamType(isExamQuestion, searchRequest);
    }

    private Map<String, Object> searchAllQuestionsOrSolutionsByExamType(String isExamQuestion, SearchRequest searchRequest) {
        QueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery(SearchPrefixWord.QUESTION_EXAM.toString(), isExamQuestion);

        searchRequest.source(buildTheSearch(matchQueryBuilder, MAX_SEARCH_RESULT));

        return computeResult(SearchPrefixWord.QUESTION_EXAM.toString(),searchRequest, isExamQuestion);
    }

    private Map<String, Object> executeAndSearchQuestionOrSolutionById(String searchPrefix, String questionId, SearchRequest searchRequest) {
        QueryBuilder matchQueryBuilder = QueryBuilders
                .matchPhraseQuery(searchPrefix, questionId);
        searchRequest.source(buildTheSearch(matchQueryBuilder, LIMIT_FOR_SINGLE_QUESTION_RESULT));

        return computeResult(searchPrefix, searchRequest, questionId);
    }

    private Map<String, Object> executeAndGetQuestionOrSolutionByChapterId(String searchPrefix, String chapterId, SearchRequest searchRequest) {
        QueryBuilder matchQueryBuilder = QueryBuilders
                .matchPhraseQuery(searchPrefix, chapterId);
        searchRequest.source(buildTheSearch(matchQueryBuilder, MAX_SEARCH_RESULT_PER_CHAPTER));

        return computeResult(searchPrefix,searchRequest, chapterId);
    }

    private Map<String, Object> executeAndGetQuestionOrSolutionById(String id, String searchWorld, GetRequest getRequest) {
        final GetResponse getResponse =
                getDocumentField(id, getRequest)
                        .orElseThrow(() -> new TechnicalQuestionException("While looking for docID " + id));

        Map<String, Object> result = new HashMap<>();
        if (getResponse.getSourceAsMap() == null)
            throw new QuestionNotFoundException("No Question Found For search Criteria :" + id);
        result.putIfAbsent(getResponse.getId(), getResponse.getSourceAsMap().get(searchWorld));
        return Collections.unmodifiableMap(result);
    }


    private Optional<GetResponse> getDocumentField(String id, GetRequest getRequest) {

        try {
            return Optional.ofNullable(restHighLevelClient.
                    get(getRequest, RequestOptions.DEFAULT));
        } catch (IOException e) {
            log.error("Unsuccessful  Reception of docs for chapId ={}, error message = [{}]", id, e.getLocalizedMessage());
            throw new TechnicalQuestionException("Unsuccessful  Reception of docs " + id +"  : error message = " + e.getLocalizedMessage());
        }
    }

    private Map<String, Object> computeResult(String searchFilter, SearchRequest searchRequest, String searchCriteria) {
        final Map<String, Object> results = new HashMap<>();
        SearchResponse searchResponse = getSearchResponse(searchRequest)
                .orElseThrow(() -> new TechnicalQuestionException("Unsuccessful  Reception of docs :" + searchCriteria + "  ->" + " Technical Error"));

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
           if ( searchFilter.contains(SearchPrefixWord.QUESTION.toString()))
            results.putIfAbsent(hit.getId(), hit.getSourceAsMap().get(SearchPrefixWord.QUESTION.toString()));
           else results.putIfAbsent(hit.getId(), hit.getSourceAsMap().get(SearchPrefixWord.SOLUTION.toString()));
        }

        log.info("Nb Of questions  retrieve : {} ", results.keySet().size());
        if (results.keySet().size() == 0)
            throw new QuestionNotFoundException("No Question Found For search Criteria :" + searchCriteria);

        return Collections.unmodifiableMap(results);
    }

    private Optional<SearchResponse> getSearchResponse(SearchRequest searchRequest) {
        try {
            return Optional.ofNullable(restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT));
        } catch (IOException e) {
            log.error("Unsuccessful  Reception of docs : error message = [{}]", e.getLocalizedMessage());
            throw new TechnicalQuestionException("Unsuccessful  Reception of docs : error message = " + e.getLocalizedMessage());
        }

    }


    private SearchSourceBuilder buildTheSearch(QueryBuilder matchQueryBuilder, int searchResultLimit) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(searchResultLimit);
        searchSourceBuilder.timeout(new TimeValue(REQUEST_TIME_OUT_DURATION_IN_SECONDS, TimeUnit.SECONDS));
        return searchSourceBuilder;
    }

}
