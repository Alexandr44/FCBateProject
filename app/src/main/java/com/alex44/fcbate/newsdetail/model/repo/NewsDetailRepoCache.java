package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.newsdetail.model.dto.NewsDetailDTO;
import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;

public class NewsDetailRepoCache implements INewsDetailRepoCache {

    @Override
    public NewsDetailDTO getNewsDetail(Long id) {
        final RoomNewsDetail roomNewsDetail = DatabaseRoom.getInstance().getNewsDetailDao().findById(id);
        final NewsDetailDTO newsDetailDTO = new NewsDetailDTO();
        newsDetailDTO.setBrief(roomNewsDetail.getBrief());
        newsDetailDTO.setContent(roomNewsDetail.getContent());
        newsDetailDTO.setDateCreated(roomNewsDetail.getDateCreated());
        newsDetailDTO.setId(roomNewsDetail.getId());
        newsDetailDTO.setPhotoUrl(roomNewsDetail.getPhotoUrl());
        newsDetailDTO.setTitle(roomNewsDetail.getTitle());
        return newsDetailDTO;
    }

    @Override
    public boolean putNewsDetail(NewsDetailDTO newsDetailDTO) {
        final RoomNewsDetail roomNewsDetail = new RoomNewsDetail();
        roomNewsDetail.setBrief(newsDetailDTO.getBrief());
        roomNewsDetail.setContent(newsDetailDTO.getContent());
        roomNewsDetail.setDateCreated(newsDetailDTO.getDateCreated());
        roomNewsDetail.setId(newsDetailDTO.getId());
        roomNewsDetail.setPhotoUrl(newsDetailDTO.getPhotoUrl());
        roomNewsDetail.setTitle(newsDetailDTO.getTitle());
        DatabaseRoom.getInstance().getNewsDetailDao().insert(roomNewsDetail);
        return true;
    }

}

