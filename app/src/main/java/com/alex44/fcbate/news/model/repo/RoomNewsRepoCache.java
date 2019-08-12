package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

public class RoomNewsRepoCache implements INewsRepoCache {
    @Override
    public List<NewsItemDTO> getNews(int count) {
        return null;
    }

    @Override
    public List<NewsItemDTO> getPress(int count) {
        return null;
    }

    @Override
    public List<NewsItemDTO> getDeclarations(int count) {
        return null;
    }

    @Override
    public boolean putNews(List<NewsItemDTO> news) {
        return false;
    }

    @Override
    public boolean putPress(List<NewsItemDTO> presses) {
        return false;
    }

    @Override
    public boolean putDeclarations(List<NewsItemDTO> declarations) {
        return false;
    }
}
