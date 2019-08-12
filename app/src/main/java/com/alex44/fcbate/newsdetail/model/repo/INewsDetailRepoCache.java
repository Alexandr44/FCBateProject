package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;

public interface INewsDetailRepoCache {

    NewsItemDetailDTO getNewsDetail(Long id);

    boolean putNewsDetail(NewsItemDetailDTO itemDetailDTO);

    NewsItemDetailDTO getPressDetail(Long id);

    boolean putPressDetail(NewsItemDetailDTO itemDetailDTO);

    NewsItemDetailDTO getDeclarationsDetail(Long id);

    boolean putDeclarationsDetail(NewsItemDetailDTO itemDetailDTO);

}
