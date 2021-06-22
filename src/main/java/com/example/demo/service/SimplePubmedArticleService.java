package com.example.demo.service;

import com.example.demo.entity.PubmedArticles;
import com.example.demo.repository.PubmedArticleDAO;
import com.example.demo.repository.PubmedArticlesRepository;
import com.example.demo.service.base.PubmedArticleService;
import com.example.demo.web.vm.PubmedArticleReportRequestVm;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimplePubmedArticleService implements PubmedArticleService {


  private final PubmedArticlesRepository pubmedArticlesRepository;
  private final PubmedArticleDAO pubmedArticleDAO;

  public SimplePubmedArticleService(
      PubmedArticlesRepository pubmedArticlesRepository,
      PubmedArticleDAO pubmedArticleDAO) {
    this.pubmedArticlesRepository = pubmedArticlesRepository;
    this.pubmedArticleDAO = pubmedArticleDAO;
  }

  @Override
  public List<PubmedArticles> getReport(PubmedArticleReportRequestVm requestVm) {
    return pubmedArticleDAO.getPubmedArticles(requestVm);
  }




}
