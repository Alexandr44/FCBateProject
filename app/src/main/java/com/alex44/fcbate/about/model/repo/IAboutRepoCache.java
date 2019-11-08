package com.alex44.fcbate.about.model.repo;

import com.alex44.fcbate.about.model.dto.AboutDTO;

public interface IAboutRepoCache {

    boolean putAbout(AboutDTO aboutDTO);

    AboutDTO getAbout();

}
