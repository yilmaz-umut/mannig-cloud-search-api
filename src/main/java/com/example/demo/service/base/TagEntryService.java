package com.example.demo.service.base;

import com.example.demo.entity.TagEntry;
import com.example.demo.web.vm.QueryTagEntryRequest;
import com.example.demo.web.vm.SaveTagRequestVm;
import org.springframework.transaction.annotation.Transactional;

public interface TagEntryService {

  @Transactional
  void saveTags(SaveTagRequestVm saveTagRequestVm);

  TagEntry queryByArticleId(QueryTagEntryRequest queryTagEntryRequest);
}
