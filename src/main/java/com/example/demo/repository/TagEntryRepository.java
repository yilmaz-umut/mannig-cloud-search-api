package com.example.demo.repository;

import com.example.demo.entity.TagEntry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagEntryRepository extends PagingAndSortingRepository<TagEntry, String> {

  Optional<TagEntry> findFirstByArticleIdAndUsername(String articeId, String username);

}
