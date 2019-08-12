package com.alex44.fcbate.newsdetail.model.api;

import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.alex44.fcbate.common.ApiStrings.DECLARATION_URL;
import static com.alex44.fcbate.common.ApiStrings.NEWS_URL;
import static com.alex44.fcbate.common.ApiStrings.PRESS_URL;

public interface INewsDetailSource {

    @GET(NEWS_URL+"/{id}")
    Maybe<NewsItemDetailDTO> getNewsDetail(@Path("id") Long id);

    @GET(PRESS_URL+"/{id}")
    Maybe<NewsItemDetailDTO> getPressDetail(@Path("id") Long id);

    @GET(DECLARATION_URL+"/{id}")
    Maybe<NewsItemDetailDTO> getDeclarationsDetail(@Path("id") Long id);

}
