package com.alex44.fcbate.news.model.repo;

import com.alex44.fcbate.news.model.dto.DeclarationDTO;
import com.alex44.fcbate.news.model.dto.NewsDTO;
import com.alex44.fcbate.news.model.dto.PressDTO;

import java.util.List;

import io.reactivex.Maybe;

public interface INewsRepo {

    Maybe<List<NewsDTO>> getNews();

    Maybe<List<PressDTO>> getPresses();

    Maybe<List<DeclarationDTO>> getDeclarations();

}
