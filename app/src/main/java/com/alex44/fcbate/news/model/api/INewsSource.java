package com.alex44.fcbate.news.model.api;

import com.alex44.fcbate.news.model.dto.DeclarationDTO;
import com.alex44.fcbate.news.model.dto.NewsDTO;
import com.alex44.fcbate.news.model.dto.PressDTO;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.alex44.fcbate.common.ApiStrings.DECLARATION_URL;
import static com.alex44.fcbate.common.ApiStrings.NEWS_URL;
import static com.alex44.fcbate.common.ApiStrings.PRESS_URL;

public interface INewsSource {

    @GET(NEWS_URL+"?offset=0")
    Maybe<List<NewsDTO>> getNews(@Query("limit") int limit);

    @GET(PRESS_URL+"?offset=0")
    Maybe<List<PressDTO>> getPresses(@Query("limit") int limit);

    @GET(DECLARATION_URL+"?offset=0")
    Maybe<List<DeclarationDTO>> getDeclarations(@Query("limit") int limit);

}
