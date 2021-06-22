package com.example.demo.web.rest;

import com.example.demo.entity.PubmedArticles;
import com.example.demo.service.base.PubmedArticleService;
import com.example.demo.web.vm.PubmedArticleReportRequestVm;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pubmed-articles")
public class PubmedArticleResource {

  private final PubmedArticleService pubmedArticleService;

  public PubmedArticleResource(
      PubmedArticleService pubmedArticleService) {
    this.pubmedArticleService = pubmedArticleService;
  }


  @PostMapping("/report")
  public List<PubmedArticles> getReport(@RequestBody PubmedArticleReportRequestVm requestVm){
     return pubmedArticleService.getReport(requestVm);
  }




}
