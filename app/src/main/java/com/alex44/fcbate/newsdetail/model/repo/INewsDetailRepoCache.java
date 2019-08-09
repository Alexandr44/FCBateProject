package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;

public interface INewsDetailRepoCache {

    NewsDetailDTO getNewsDetail(Long id);

    boolean putNewsDetail(NewsDetailDTO newsDetailDTO);

}
