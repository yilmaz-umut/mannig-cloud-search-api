package com.example.demo.integration.wikidata.model;

public class WikiDataQueryRequest {

  private String term;

  private String searchContinue;

  private boolean requestForMore = false;


  public String getTerm() {
    return term;
  }

  public String getSearchContinue() {
    return searchContinue;
  }

  public boolean isRequestForMore() {
    return requestForMore;
  }
}
