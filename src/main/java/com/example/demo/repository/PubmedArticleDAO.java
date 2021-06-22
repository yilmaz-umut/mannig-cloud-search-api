package com.example.demo.repository;

import com.example.demo.entity.PubmedArticles;
import com.example.demo.web.vm.PubmedArticleReportRequestVm;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class PubmedArticleDAO {

  private final MongoTemplate mongoTemplate;
  private final TagEntryDAO tagEntryDAO;

  public PubmedArticleDAO(MongoTemplate mongoTemplate,
      TagEntryDAO tagEntryDAO) {
    this.mongoTemplate = mongoTemplate;
    this.tagEntryDAO = tagEntryDAO;
  }

  public List<PubmedArticles> getPubmedArticles(PubmedArticleReportRequestVm requestVm) {
    Query dynamicQuery = new Query();

    if (StringUtils.isNotBlank(requestVm.getArticleTitle())) {
      Criteria criteriaArticleTitle = Criteria.where("articleTitle")
          .regex(requestVm.getArticleTitle());
      dynamicQuery.addCriteria(criteriaArticleTitle);
    }

    if (StringUtils.isNotBlank(requestVm.getDate())) {

      final LocalDate lPublicationDate = LocalDate.parse(requestVm.getDate());
      final String day = DateTimeFormatter.ofPattern("dd").format(lPublicationDate);
      final String month = DateTimeFormatter.ofPattern("MM").format(lPublicationDate);
      final String year = DateTimeFormatter.ofPattern("yyyy").format(lPublicationDate);

      dynamicQuery.addCriteria(Criteria.where("articleDate.year").is(year));
      dynamicQuery.addCriteria(Criteria.where("articleDate.month").is(month));
      dynamicQuery.addCriteria(Criteria.where("articleDate.day").is(day));
    }

    if (StringUtils.isNotBlank(requestVm.getPmid())) {
      dynamicQuery.addCriteria(Criteria.where("pmid").is(Long.valueOf(requestVm.getPmid())));
    }

    if (StringUtils.isNotBlank(requestVm.getAuthor())) {
      dynamicQuery.addCriteria(Criteria.where("authors.fullname")
          .regex(requestVm.getAuthor()));
    }

    if (StringUtils.isNotBlank(requestVm.getAbstractText())) {
      dynamicQuery.addCriteria(Criteria.where("publicationAbstracts.abstractTxt")
          .regex(requestVm.getAbstractText()));
    }

    if (StringUtils.isNotBlank(requestVm.getKeyword())) {
      dynamicQuery.addCriteria(Criteria.where("articleKeyWords.keyword")
          .regex(requestVm.getKeyword()));
    }

    if (StringUtils.isNotBlank(requestVm.getTagName())) {
      final List<String> articleIds = tagEntryDAO.getArticleIdsByTagName(requestVm.getTagName());
      if (!articleIds.isEmpty()) {
        dynamicQuery.addCriteria(Criteria.where("id").in(articleIds));
      }
    }

    return mongoTemplate.find(dynamicQuery, PubmedArticles.class);
  }


}
