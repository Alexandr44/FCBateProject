package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;

import io.reactivex.Maybe;

public interface INewsDetailRepo {

    Maybe<NewsItemDetailDTO> getNewsDetail(Long id);

    Maybe<NewsItemDetailDTO> getPressDetail(Long id);

    Maybe<NewsItemDetailDTO> getDeclarationDetail(Long id);

}
