package com.example.demo.web.rest;

import com.example.demo.entity.TagEntry;
import com.example.demo.service.base.TagEntryService;
import com.example.demo.web.vm.QueryTagEntryRequest;
import com.example.demo.web.vm.SaveTagRequestVm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag-entries")
public class TagEntryResource {

  private final TagEntryService tagEntryService;

  public TagEntryResource(TagEntryService tagEntryService) {
    this.tagEntryService = tagEntryService;
  }


  @PostMapping("/save-tag")
  public void saveTags(@RequestBody SaveTagRequestVm saveTagRequestVm){
    tagEntryService.saveTags(saveTagRequestVm);
  }


  @PostMapping("/by-article-id")
  public TagEntry queryByArticleId(@RequestBody QueryTagEntryRequest queryTagEntryRequest){
    return tagEntryService.queryByArticleId(queryTagEntryRequest);
  }

}
