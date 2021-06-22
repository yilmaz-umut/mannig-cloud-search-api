package com.example.demo.repository;

import com.example.demo.entity.PubmedArticles;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PubmedArticlesRepository extends
    PagingAndSortingRepository<PubmedArticles, String> {


}
