package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

public interface INewsRepoCache {

    List<NewsItemDTO> getNews(int count);

    List<NewsItemDTO> getPress(int count);

    List<NewsItemDTO> getDeclarations(int count);

    boolean putNews(List<NewsItemDTO> news);

    boolean putPress(List<NewsItemDTO> presses);

    boolean putDeclarations(List<NewsItemDTO> declarations);
}
