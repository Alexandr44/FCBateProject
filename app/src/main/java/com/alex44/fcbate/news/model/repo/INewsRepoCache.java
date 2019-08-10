package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.DeclarationDTO;
import com.alex44.fcbate.news.model.dto.NewsDTO;
import com.alex44.fcbate.news.model.dto.PressDTO;

import java.util.List;

public interface INewsRepoCache {

    List<NewsDTO> getNews(int count);

    List<PressDTO> getPress(int count);

    List<DeclarationDTO> getDeclarations(int count);

    boolean putNews(List<NewsDTO> news);

    boolean putPress(List<PressDTO> presses);

    boolean putDeclarations(List<DeclarationDTO> declarations);
}
