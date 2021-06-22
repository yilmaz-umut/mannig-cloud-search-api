package com.example.demo.web.rest;

import com.example.demo.integration.wikidata.WikidataIntegrationService;
import com.example.demo.integration.wikidata.model.WikiDataQueryRequest;
import com.example.demo.integration.wikidata.model.WikiDataQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wiki-data")
public class WikidataIntegrationResource {


  private final WikidataIntegrationService wikidataIntegrationService;

  public WikidataIntegrationResource(
      WikidataIntegrationService wikidataIntegrationService) {
    this.wikidataIntegrationService = wikidataIntegrationService;
  }


  @PostMapping("/search-entity")
  public WikiDataQueryResponse getReport(@RequestBody WikiDataQueryRequest wikiDataQueryRequest)
      throws JsonProcessingException {
     return wikidataIntegrationService.query(wikiDataQueryRequest);
  }



}
