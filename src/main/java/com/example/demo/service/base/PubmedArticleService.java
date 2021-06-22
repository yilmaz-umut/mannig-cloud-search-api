package com.example.demo.service.base;

import com.example.demo.entity.PubmedArticles;
import com.example.demo.integration.wikidata.MediaWikiApiErrorException;
import com.example.demo.integration.wikidata.model.WikiDataQueryRequest;
import com.example.demo.integration.wikidata.model.WikiDataQueryResponse;
import com.example.demo.web.vm.PubmedArticleReportRequestVm;
import com.example.demo.web.vm.SaveTagRequestVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface PubmedArticleService {

  List<PubmedArticles> getReport(PubmedArticleReportRequestVm pubmedArticleReportRequest);

}
