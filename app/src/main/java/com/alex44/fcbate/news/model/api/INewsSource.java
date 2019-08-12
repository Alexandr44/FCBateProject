package com.alex44.fcbate.news.model.api;

import com.alex44.fcbate.news.model.dto.NewsItemDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.alex44.fcbate.common.ApiStrings.DECLARATION_URL;
import static com.alex44.fcbate.common.ApiStrings.NEWS_URL;
import static com.alex44.fcbate.common.ApiStrings.PRESS_URL;

public interface INewsSource {

    @GET(NEWS_URL+"?offset=0")
    Maybe<List<NewsItemDTO>> getNews(@Query("limit") int limit);

    @GET(PRESS_URL+"?offset=0")
    Maybe<List<NewsItemDTO>> getPresses(@Query("limit") int limit);

    @GET(DECLARATION_URL+"?offset=0")
    Maybe<List<NewsItemDTO>> getDeclarations(@Query("limit") int limit);

}
