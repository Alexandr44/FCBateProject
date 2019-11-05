package com.alex44.fcbate.about.model.repo;

import com.alex44.fcbate.about.model.dto.AboutDTO;

import io.reactivex.Maybe;

public interface IAboutRepo {

    Maybe<AboutDTO> getAbout();

}
