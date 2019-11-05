package com.alex44.fcbate.about.model.api;

import com.alex44.fcbate.about.model.dto.AboutDTO;

import io.reactivex.Maybe;
import retrofit2.http.GET;

import static com.alex44.fcbate.common.ApiStrings.ABOUT_URL;

public interface IAboutSource {

    @GET(ABOUT_URL)
    Maybe<AboutDTO> getAbout();

}
