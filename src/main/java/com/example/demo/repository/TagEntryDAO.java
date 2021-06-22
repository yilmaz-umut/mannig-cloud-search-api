package com.example.demo.repository;

import com.example.demo.entity.TagEntry;
import com.example.demo.security.utils.SecurityUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class TagEntryDAO {

  private final MongoTemplate mongoTemplate;

  public TagEntryDAO(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }


  public List<String> getArticleIdsByTagName(final String tagName){
    Criteria criteria = new Criteria();

    criteria.orOperator(
        Criteria.where("tags.concepturi").regex(tagName)
        ,Criteria.where("tags.description").regex(tagName)
        ,Criteria.where("tags.label").regex(tagName)
        ,Criteria.where("tags.customTagLabel").regex(tagName));

    final String currentUser = SecurityUtils.getCurrentUsername();
    criteria.andOperator(Criteria.where("username").is(currentUser));
    Query dynamicQueryForTagEntries = new Query(criteria);

    List<TagEntry> tagEntries =  mongoTemplate.find(dynamicQueryForTagEntries, TagEntry.class);
    return tagEntries.stream().map(TagEntry::getArticleId).collect(Collectors.toList());
  }

}
