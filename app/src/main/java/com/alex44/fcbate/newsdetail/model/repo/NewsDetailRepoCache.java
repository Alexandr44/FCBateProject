package com.alex44.fcbate.newsdetail.model.repo;

import com.alex44.fcbate.common.model.db.DatabaseRoom;
import com.alex44.fcbate.newsdetail.model.dto.NewsItemDetailDTO;
import com.alex44.fcbate.newsdetail.model.room.RoomNewsDetail;

public class NewsDetailRepoCache implements INewsDetailRepoCache {

    @Override
    public NewsItemDetailDTO getNewsDetail(Long id) {
        final RoomNewsDetail roomNewsDetail = DatabaseRoom.getInstance().getNewsDetailDao().findById(id);
        final NewsItemDetailDTO newsDetailDTO = new NewsItemDetailDTO();
        newsDetailDTO.setBrief(roomNewsDetail.getBrief());
        newsDetailDTO.setContent(roomNewsDetail.getContent());
        newsDetailDTO.setDateCreated(roomNewsDetail.getDateCreated());
        newsDetailDTO.setId(roomNewsDetail.getId());
        newsDetailDTO.setPhotoUrl(roomNewsDetail.getPhotoUrl());
        newsDetailDTO.setTitle(roomNewsDetail.getTitle());
        return newsDetailDTO;
    }

    @Override
    public boolean putNewsDetail(NewsItemDetailDTO newsDetailDTO) {
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

    @Override
    public NewsItemDetailDTO getPressDetail(Long id) {
        return null;
    }

    @Override
    public boolean putPressDetail(NewsItemDetailDTO itemDetailDTO) {
        return false;
    }

    @Override
    public NewsItemDetailDTO getDeclarationsDetail(Long id) {
        return null;
    }

    @Override
    public boolean putDeclarationsDetail(NewsItemDetailDTO itemDetailDTO) {
        return false;
    }

}

