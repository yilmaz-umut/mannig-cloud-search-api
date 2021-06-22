package com.example.demo.service;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.demo.entity.*;
import com.example.demo.integration.meaningcloud.MeaningCloudIntegrationService;
import com.example.demo.pubmedquery.PubmedEFetchHandler;
import com.example.demo.repository.PubmedArticlesRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import reciter.model.pubmed.MedlineCitationDate;
import reciter.model.pubmed.PubMedArticle;

@Service
public class PubmedArticlesService {

  private final PubmedArticlesRepository pubmedArticlesRepository;
  private final MeaningCloudIntegrationService meaningCloudIntegrationService;


  public PubmedArticlesService(
          PubmedArticlesRepository pubmedArticlesRepository, MeaningCloudIntegrationService meaningCloudIntegrationService) {
    this.pubmedArticlesRepository = pubmedArticlesRepository;
    this.meaningCloudIntegrationService = meaningCloudIntegrationService;
  }


  @Transactional
  public void parseEndSave(ResponseEntity<String> fetchResponse)
      throws ParserConfigurationException, IOException, SAXException {

    List<PubMedArticle> pubMedArticlesFromEntrez = getSubMedArticlesFromEntrez(fetchResponse);
    saveAll(pubMedArticlesFromEntrez);
  }

  private List<PubMedArticle> getSubMedArticlesFromEntrez(ResponseEntity<String> fetchResponse)
      throws ParserConfigurationException, SAXException, IOException {
    PubmedEFetchHandler pubmedEFetchHandler = new PubmedEFetchHandler();
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    InputStream targetStream = new ByteArrayInputStream(fetchResponse.getBody().getBytes(
        StandardCharsets.UTF_8));

    saxParser.parse(targetStream, pubmedEFetchHandler);
    return pubmedEFetchHandler.getPubmedArticles();
  }


  private void saveAll(List<PubMedArticle> pubMedArticlesFromEntrez) {
    List<PubmedArticles> pubmedArticles = pubMedArticlesFromEntrez.stream().map(pubMedArticle -> {

      PubmedArticles newArticle = new PubmedArticles();
      newArticle.setId(UUID.randomUUID().toString());
      newArticle.setPmid(pubMedArticle.getMedlinecitation().getMedlinecitationpmid().getPmid());
      newArticle.setArticleTitle(pubMedArticle.getMedlinecitation().getArticle().getArticletitle());

      setArticleDate(pubMedArticle, newArticle);
      setArticleKeyWords(pubMedArticle, newArticle);
      setAuthors(pubMedArticle, newArticle);
      setPublicationAbstracts(pubMedArticle, newArticle);

      final String keyword = newArticle.getArticleTitle() + " " + newArticle.getPublicationAbstracts();
      try {
        ATag aTag = meaningCloudIntegrationService.test(keyword);
        newArticle.setaTag(aTag);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }


      return newArticle;
    }).collect(Collectors.toList());

    pubmedArticlesRepository.saveAll(pubmedArticles);
  }


  private void setPublicationAbstracts(PubMedArticle pubMedArticle, PubmedArticles newArticle) {
    try {
      List<PublicationAbstract> publicationAbstracts = pubMedArticle.getMedlinecitation()
          .getArticle()
          .getPublicationAbstract().getAbstractTexts().stream().map(
              medlineCitationArticleAbstractText -> new PublicationAbstract(
                  medlineCitationArticleAbstractText.getAbstractText()))
          .collect(Collectors.toList());

      newArticle.setPublicationAbstracts(publicationAbstracts);
    } catch (Exception e) {

    }
  }

  private void setAuthors(PubMedArticle pubMedArticle, PubmedArticles newArticle) {

    try {
      List<Author> authors = pubMedArticle.getMedlinecitation().getArticle().getAuthorlist()
          .stream()
          .map(medlineCitationArticleAuthor -> {
            Author author = new Author();
            author.setForename(medlineCitationArticleAuthor.getForename());
            author.setLastname(medlineCitationArticleAuthor.getLastname());
            author.setFullname(author.getForename() + " " + author.getLastname());

            return author;
          }).collect(Collectors.toList());

      newArticle.setAuthors(authors);
    } catch (Exception exception) {

    }
  }

  private void setArticleKeyWords(PubMedArticle pubMedArticle, PubmedArticles newArticle) {

    try {
      List<ArticleKeyword> articleKeywords = pubMedArticle.getMedlinecitation().getKeywordlist()
          .getKeywordlist().stream()
          .map(medlineCitationKeyword -> new ArticleKeyword(medlineCitationKeyword.getKeyword()))
          .collect(Collectors.toList());

      newArticle.setArticleKeyWords(articleKeywords);
    } catch (Exception ignored) {

    }

  }


  private void setArticleDate(PubMedArticle pubMedArticleFromEntrez, PubmedArticles newArticle) {

    try {
      MedlineCitationDate medlineCitationDate = pubMedArticleFromEntrez.getMedlinecitation()
          .getArticle().getArticledate();
      ArticleDate articleDate = new ArticleDate();
      articleDate.setDay(medlineCitationDate.getDay());
      articleDate.setMonth(medlineCitationDate.getMonth());
      articleDate.setYear(medlineCitationDate.getYear());

      newArticle.setArticleDate(articleDate);
    } catch (Exception ignored) {

    }

  }
}
