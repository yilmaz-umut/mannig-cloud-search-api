package com.example.demo.integration.wikidata.model;

import java.util.ArrayList;
import java.util.List;

public class WikiDataQueryResponse {

  private List<WbSearchEntitiesResult> results = new ArrayList<>();

  private String searchContinue;



  public List<WbSearchEntitiesResult> getResults() {
    return results;
  }

  public void setResults(List<WbSearchEntitiesResult> results) {
    this.results = results;
  }

  public String getSearchContinue() {
    return searchContinue;
  }

  public void setSearchContinue(String searchContinue) {
    this.searchContinue = searchContinue;
  }
}
