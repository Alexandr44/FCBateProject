package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.DeclarationDTO;
import com.alex44.fcbate.news.model.dto.NewsDTO;
import com.alex44.fcbate.news.model.dto.PressDTO;

import java.util.List;

public class RoomNewsRepoCache implements INewsRepoCache {
    @Override
    public List<NewsDTO> getNews(int count) {
        return null;
    }

    @Override
    public List<PressDTO> getPress(int count) {
        return null;
    }

    @Override
    public List<DeclarationDTO> getDeclarations(int count) {
        return null;
    }

    @Override
    public boolean putNews(List<NewsDTO> news) {
        return false;
    }

    @Override
    public boolean putPress(List<PressDTO> presses) {
        return false;
    }

    @Override
    public boolean putDeclarations(List<DeclarationDTO> declarations) {
        return false;
    }
}
