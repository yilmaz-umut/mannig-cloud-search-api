package com.example.demo.entity;


import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.xspec.S;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pubmed_articles")
public class PubmedArticles implements Serializable {

  @Id
  private String id;

  private Long pmid;

  private ArticleDate articleDate;

  private String articleTitle;

  private List<Author> authors;

  private List<PublicationAbstract> publicationAbstracts;

  private List<ArticleKeyword> articleKeyWords;

  private ATag aTag;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getPmid() {
    return pmid;
  }

  public void setPmid(Long pmid) {
    this.pmid = pmid;
  }

  public ArticleDate getArticleDate() {
    return articleDate;
  }

  public void setArticleDate(ArticleDate articleDate) {
    this.articleDate = articleDate;
  }

  public String getArticleTitle() {
    return articleTitle;
  }

  public void setArticleTitle(String articleTitle) {
    this.articleTitle = articleTitle;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public List<PublicationAbstract> getPublicationAbstracts() {
    return publicationAbstracts;
  }

  public void setPublicationAbstracts(
      List<PublicationAbstract> publicationAbstracts) {
    this.publicationAbstracts = publicationAbstracts;
  }

  public List<ArticleKeyword> getArticleKeyWords() {
    return articleKeyWords;
  }

  public void setArticleKeyWords(List<ArticleKeyword> articleKeyWords) {
    this.articleKeyWords = articleKeyWords;
  }

  public ATag getaTag() {
    return aTag;
  }

  public void setaTag(ATag aTag) {
    this.aTag = aTag;
  }
}
