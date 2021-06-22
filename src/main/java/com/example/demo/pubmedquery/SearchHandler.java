package com.example.demo.pubmedquery;

import com.example.demo.service.PubmedArticlesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

@Component
public class SearchHandler {

  private final String term;
  private final RestTemplate restTemplate;
  private final PubmedArticlesService pubmedArticlesService;

  public SearchHandler(RestTemplate restTemplate,
      PubmedArticlesService pubmedArticlesService) {
    this.restTemplate = restTemplate;
    this.pubmedArticlesService = pubmedArticlesService;
    term = "kidney+disease";
  }


  public JsonNode executeSearchForIds() throws JsonProcessingException {
    PubmedXmlQuery pubmedXmlQuery = new PubmedXmlQuery(term);
    ResponseEntity<String> response
        = restTemplate.getForEntity(pubmedXmlQuery.buildESearchQuery(), String.class);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response.getBody());
    JsonNode esearchresult = root.path("esearchresult");
    return esearchresult.path("idlist");
  }

  private ResponseEntity<String> getResponseEntityForEntrezFetch(StringBuilder pmids ){
    PubmedXmlQuery pubmedXmlQuery = new PubmedXmlQuery(term);
    pmids.deleteCharAt(0);
    final String fetchQuery = pubmedXmlQuery.buildEFetchQuery(pmids.toString());

    return restTemplate.  getForEntity(fetchQuery, String.class);
  }


  @Transactional
  public void executeESearchQuery() throws IOException, ParserConfigurationException, SAXException {

    JsonNode idlist = executeSearchForIds();

    StringBuilder pmids = new StringBuilder();
    int counter = 1;
    for (final JsonNode objNode : idlist) {
      final String pmid = objNode.textValue();
      pmids.append(",").append(pmid);
      if ( counter == 10){
        ResponseEntity<String> fetchResponse = getResponseEntityForEntrezFetch(pmids);
        pubmedArticlesService.parseEndSave(fetchResponse);
        counter = 1;
        pmids.setLength(0);
      }else {
        counter++;
      }
    }

  }




}
