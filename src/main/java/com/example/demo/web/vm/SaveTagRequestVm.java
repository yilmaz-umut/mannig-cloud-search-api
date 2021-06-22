package com.example.demo.web.vm;

import com.example.demo.entity.Tag;
import java.util.ArrayList;
import java.util.List;

public class SaveTagRequestVm {

  private List<String> articleIds = new ArrayList<>();

  private List<Tag> wikiDatas  = new ArrayList<>();

  public List<String> getArticleIds() {
    return articleIds;
  }

  public void setArticleIds(List<String> articleIds) {
    this.articleIds = articleIds;
  }

  public List<Tag> getWikiDatas() {
    return wikiDatas;
  }

  public void setWikiDatas(List<Tag> wikiDatas) {
    this.wikiDatas = wikiDatas;
  }
}
