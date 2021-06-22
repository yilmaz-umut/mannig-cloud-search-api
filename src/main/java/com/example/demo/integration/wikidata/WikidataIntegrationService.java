package com.example.demo.integration.wikidata;


import com.example.demo.integration.wikidata.model.JacksonWbSearchEntitiesResult;
import com.example.demo.integration.wikidata.model.WikiDataQueryRequest;
import com.example.demo.integration.wikidata.model.WbSearchEntitiesResult;
import com.example.demo.integration.wikidata.model.WikiDataQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WikidataIntegrationService {

  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;


  public WikidataIntegrationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    mapper= new ObjectMapper();
  }


  public WikiDataQueryResponse query(WikiDataQueryRequest wikiDataQueryRequest)
      throws JsonProcessingException {

    ResponseEntity<String> responseEntity = getQueryResponseEntity(
        wikiDataQueryRequest);

    return getWikidataQueryResponse(responseEntity);
  }


  private WikiDataQueryResponse getWikidataQueryResponse(ResponseEntity<String> responseEntity) throws JsonProcessingException {

    JsonNode root = mapper.readTree(responseEntity.getBody());
    checkErrors(root);

    final List<WbSearchEntitiesResult> wbSearchEntitiesResults = getWikiSearchEntitiesResult(root);
    final String searchContinue = String.valueOf(getSearchContinueResult(root));

    WikiDataQueryResponse wikiDataQueryResponse = new WikiDataQueryResponse();
    wikiDataQueryResponse.setResults(wbSearchEntitiesResults);
    wikiDataQueryResponse.setSearchContinue(searchContinue);

    return wikiDataQueryResponse;
  }

  private int getSearchContinueResult(JsonNode root){
    JsonNode searchContinue = root.path("search-continue");
    return searchContinue.intValue();
  }



  private List<WbSearchEntitiesResult> getWikiSearchEntitiesResult(JsonNode root){
    JsonNode entities = root.path("search");
    List<WbSearchEntitiesResult> results = new ArrayList<>();
    for (JsonNode entityNode : entities) {
      try {
        JacksonWbSearchEntitiesResult ed = mapper.treeToValue(entityNode,
            JacksonWbSearchEntitiesResult.class);
        results.add(ed);
      } catch (JsonProcessingException e) {
        return results;
      }
    }
    return results;
  }



  private ResponseEntity<String> getQueryResponseEntity(
      WikiDataQueryRequest wikiDataQueryRequest) {
    final String baseSearchUrl = "https://www.wikidata.org/w/api.php?action=wbsearchentities&search=";
    final String additionalParams = "&format=json&errorformat=plaintext&language=en&uselang=en&type=item";

    String searchQuery = baseSearchUrl + wikiDataQueryRequest.getTerm() + additionalParams;
    if ( wikiDataQueryRequest.isRequestForMore()){
      searchQuery = searchQuery +  "&continue=" + wikiDataQueryRequest.getSearchContinue();
    }
    return restTemplate.getForEntity(searchQuery, String.class);
  }

  protected void checkErrors(JsonNode root) {
    if (root.has("error")) {
      JsonNode errorNode = root.path("error");
      String code = errorNode.path("code").asText("UNKNOWN");
      String info = errorNode.path("info").asText("No details provided");

      if (errorNode.has("lag") && "maxlag".equals(code)) {
        throw new RuntimeException(info);
      }
    }
  }

}
