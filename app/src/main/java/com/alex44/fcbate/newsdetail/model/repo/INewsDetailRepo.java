package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;

import io.reactivex.Maybe;

public interface INewsDetailRepo {

    Maybe<NewsDetailDTO> getNewsDetail(Long id);

}
