package com.example.demo.service;

import com.example.demo.entity.TagEntry;
import com.example.demo.repository.TagEntryRepository;
import com.example.demo.security.utils.SecurityUtils;
import com.example.demo.service.base.TagEntryService;
import com.example.demo.web.vm.QueryTagEntryRequest;
import com.example.demo.web.vm.SaveTagRequestVm;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SimpleTagEntryService implements TagEntryService {

  private final TagEntryRepository repository;

  public SimpleTagEntryService(TagEntryRepository repository) {
    this.repository = repository;
  }

  @Transactional
  @Override
  public void saveTags(SaveTagRequestVm saveTagRequestVm) {

    Assert.notEmpty(saveTagRequestVm.getArticleIds(), "Makale Id listesi zorunludur.");
    Assert.notEmpty(saveTagRequestVm.getWikiDatas(), "Etiket listesi zorunludur.");

    final String currentUsername = SecurityUtils.getCurrentUsername();
    List<TagEntry> tagEntries = saveTagRequestVm.getArticleIds().stream().map(articleId -> {
      TagEntry tagEntry = repository
          .findFirstByArticleIdAndUsername(articleId, currentUsername)
          .orElse(new TagEntry());;
      tagEntry.setTags(saveTagRequestVm.getWikiDatas());
      tagEntry.setUsername(currentUsername);
      tagEntry.setArticleId(articleId);

      return tagEntry;
    }).collect(Collectors.toList());

    repository.saveAll(tagEntries);
  }

  @Override
  public TagEntry queryByArticleId(QueryTagEntryRequest queryTagEntryRequest) {
    Assert.hasText(queryTagEntryRequest.getArticleId(), "Makale No zorunludur.");
    final String currentUsername = SecurityUtils.getCurrentUsername();

    return repository
        .findFirstByArticleIdAndUsername(queryTagEntryRequest.getArticleId(), currentUsername)
        .orElse(null);
  }


}
