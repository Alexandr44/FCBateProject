package com.alex44.fcbate.newsdetail.model.api;

import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.alex44.fcbate.common.ApiStrings.NEWS_DETAIL_URL;

public interface INewsDetailSource {

    @GET(NEWS_DETAIL_URL+"/{id}")
    Maybe<NewsDetailDTO> getNewsDetail(@Path("id") Long id);

}
