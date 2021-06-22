package com.example.demo.entity;

public class Tag {
  
  private String id;

  private String concepturi;

  private String description;

  private String label;

  private String matchText;

  private String customTagLabel;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConcepturi() {
    return concepturi;
  }

  public void setConcepturi(String concepturi) {
    this.concepturi = concepturi;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getMatchText() {
    return matchText;
  }

  public void setMatchText(String matchText) {
    this.matchText = matchText;
  }

  public String getCustomTagLabel() {
    return customTagLabel;
  }

  public void setCustomTagLabel(String customTagLabel) {
    this.customTagLabel = customTagLabel;
  }
}
