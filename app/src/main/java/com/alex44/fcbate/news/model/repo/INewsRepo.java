package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface INewsRepo {

    Maybe<List<NewsItemDTO>> getNews();

    Maybe<List<NewsItemDTO>> getPresses();

    Maybe<List<NewsItemDTO>> getDeclarations();

}
